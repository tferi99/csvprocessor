package org.ftoth.cvsproc.general;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvGroupProcessor<T>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessor.class);

    protected Class<T> inputModelClass;
    protected CsvReader<T> reader;
    protected CsvWriter<T> processingResultWriter;
    protected CsvWriter<T> restWriter = null;
    protected List<List<T>> groups = null;
    protected CsvGroup<T> currentGroup = null;
    protected boolean writeNonValidGroupsIntoRestFile = true;
    protected String inputFile;
    protected List<T> rest = new ArrayList<T>();

    public CsvGroupProcessor(String inputFile, Class<T> inputModelClass) throws Exception
    {
        this.inputFile = inputFile;
        this.inputModelClass = inputModelClass;
        reader = new CsvReader<T>(inputFile, inputModelClass);
    }

    public boolean isWriteNonValidGroupsIntoRestFile() {
        return writeNonValidGroupsIntoRestFile;
    }

    public void process()
    {
        Object prevId = null;

        try {
            List<T> beans = reader.getBeans();
            for (T bean : beans) {
                Object currentId = getGroupIdFromItem(bean);

                // on first data row
                if (currentGroup == null) {
                    createNewGroup(bean);
                    prevId = currentId;
                    createDebugInfoFromIncomingRecord(currentId, bean);
                    continue;
                }

                // identifying group
                if (prevId.equals(currentId)) {
                    // add to current group
                    currentGroup.add(bean);
                } else {
                    onGroupLoaded();                    // precess last group
                    createNewGroup(bean);               // create a new group
                }
                createDebugInfoFromIncomingRecord(currentId, bean);
                prevId = currentId;
            }
            // not processed, last group
            if (beans.size() > 0) {
                onGroupLoaded();
            }

            // write rest
            writeRestToRestFile();
        }
        catch (Exception e) {
            log.error("Error occured during processing.", e);
            e.printStackTrace();
        }
        finally {
            cleanup();
        }
    }

    private void writeRestToRestFile()
    {
        String ext = FilenameUtils.getExtension(inputFile);
        String restFile = FilenameUtils.removeExtension(inputFile) + "-rest." + ext;

        CsvWriter<T> restWriter = new CsvWriter<T>(restFile, inputFile, inputModelClass);
        try {
            restWriter.write(rest);
        } catch (Exception e) {
            log.error("Error during writing rest file: " + restFile, e);
            e.printStackTrace();
        }
        finally {
            restWriter.close();
        }
    }

    private void cleanup() {
        if (processingResultWriter != null) {
            processingResultWriter.close();
        }
        if (restWriter != null) {
            restWriter.close();
        }
    }

    private void createNewGroup(T firstItem)
    {
        currentGroup = new CsvGroup<T>();
        if (log.isDebugEnabled()) {
            Object id = getGroupIdFromItem(firstItem);
            currentGroup.setId(id);
            log.debug("-------------------------------------- new group[" + id + "] -------------------------------------------------------------");
        }
        currentGroup.add(firstItem);
    }

    private void onGroupLoaded()
    {
        if (log.isDebugEnabled()) {
            log.debug("Processing of group[" + currentGroup.getId() + "] - size: " + currentGroup.getItems().size());
        }
        if (validateGroup(currentGroup)) {
            processGroup(currentGroup);
        }
        else if (writeNonValidGroupsIntoRestFile) {
            addGroupToRest(currentGroup);
        }
    }

    private void addGroupToRest(CsvGroup<T> group)
    {
        rest.addAll(group.getItems());
    }

    /**
     * To generate values from incoming record into debug output
     *
     * @param id
     * @param bean
     */
    private void createDebugInfoFromIncomingRecord(Object id, T bean)
    {
        if (log.isDebugEnabled()) {
            StringWriter w = new StringWriter();
            CsvWriter<T> cw = new CsvWriter<T>(w, this.inputModelClass);
            try {
                cw.write(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                cw.close();
            }
            String s = w.toString();
            if (s.endsWith("\n")) {
                s = s.substring(0, s.length() - 1);
            }
            log.debug("IN record[" + id + "] [" + s + "]");
        }
    }

    protected void logValidationError(String source, String message)
    {
        if (log.isDebugEnabled()) {
            log.debug("GROUP VALIDATION ERROR FROM [" + source + "]: " + message);
        }
    }

    /**
     * Return simple or composite ID of current bean.
     *
     * @param item
     * @return
     */
    protected abstract Object getGroupIdFromItem(T item);


    /**
     * It validates group.
     * If group not valid items will be sent into REST file.
     *
     *
     * @param group CSV record group
     * @return true: valid, false: invalid
     */
    protected abstract boolean validateGroup(CsvGroup<T> group);

    /**
     * Processing current group.
     *
     * @param group CSV record group
     * @return false: group rejected, write into REST file
     */
    protected abstract void processGroup(CsvGroup<T> group);
}
