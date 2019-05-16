package org.ftoth.cvsproc.general;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.apache.log4j.Logger;

import java.io.StringWriter;
import java.util.List;

public abstract class CsvGroupProcessor<T>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessor.class);

    protected CsvReader<T> reader;
    protected CsvWriter<T> processingResultWriter;
    protected CsvWriter<T> restWriter = null;
    protected List<List<T>> groups = null;
    protected CsvGroup<T> currentGroup = null;
    protected boolean writeNonValidGroupsIntoRestFile = true;

    /**
     * This strategy contains description of columns of the (main) input CSV.
     * It will be used for tracing incoming records or if processing output has the
     * same field structure.
     */
    protected ColumnPositionMappingStrategy<T> columnWritePositionStrategy = null;

    public CsvGroupProcessor(String inputFile, ColumnPositionMappingStrategy<T> columnWritePositionStrategy) throws Exception
    {
        this.columnWritePositionStrategy = columnWritePositionStrategy;
        reader = new CsvReader<T>(inputFile);
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
        }
        catch (Exception e) {
            log.error("Error occured during processing.", e);
            e.printStackTrace();
        }
        finally {
            cleanup();
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
            writeGroupIntoRestFile(currentGroup);
        }
    }

    private void writeGroupIntoRestFile(CsvGroup<T> group) {
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
            CsvWriter<T> cw = new CsvWriter<T>(w);
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
