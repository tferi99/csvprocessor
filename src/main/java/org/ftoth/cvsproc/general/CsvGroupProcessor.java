package org.ftoth.cvsproc.general;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvGroupProcessor<I, O>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessor.class);

    protected CsvReader<I> reader;
    protected List<List<I>> groups = null;
    protected CsvGroup<I> currentGroup = null;
    protected boolean writeNonValidGroupsIntoRestFile = true;
    protected String inputFile;
    protected String outputFile;
    protected Class<I> inputModelClass;
    protected Class<O> outputModelClass;
    protected List<O> result = new ArrayList<O>();
    protected List<I> rest = new ArrayList<I>();
    private Reader outputHeaderReader = null;       // will be created on demand from names specified by getOutputHeaderColumnNames()

    private static final char DEFAULT_INPUT_SEPARATOR = ',';
    private char inputSeparator = DEFAULT_INPUT_SEPARATOR;
    private static final String DEFAULT_OUTPUT_FILE_POSTFIX = "-result";
    
    public CsvGroupProcessor(String inputFile, Class<I> inputModelClass, String outputFile, Class<O> outputModelClass) throws Exception
    {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.inputModelClass = inputModelClass;
        this.outputModelClass = outputModelClass;
        reader = new CsvReader<I>(inputFile, inputModelClass, inputSeparator);
    }

    public boolean isWriteNonValidGroupsIntoRestFile() {
        return writeNonValidGroupsIntoRestFile;
    }

    public void process()
    {
        Object prevId = null;

        try {
            List<I> beans = reader.getBeans();
            for (I bean : beans) {
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

            postProcessing();
        }
        catch (Exception e) {
            log.error("Error occured during processing.", e);
            e.printStackTrace();
        }
        finally {
            cleanup();
        }
    }

    /**
     * It writes result and non*processed items into files.
     *
     * By default 1 output file supported. If you want to add more outputs
     * override this method and add write action there.
     *
     * IMPORTANT: DON'I FORGET TO CALL super.postProcessing() FROM OVERRIDE.
     */
    protected void postProcessing()
    {
        writeResultToFile();
        writeRestToRestFile();
    }

    private void writeResultToFile()
    {
        CsvWriter<O> writer = new CsvWriter<O>(outputFile, getHeaderReader(), outputModelClass);
        try {
            writer.write(result);
        } catch (Exception e) {
            log.error("Error during writing result file: " + outputFile, e);
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }

    /**
     * I writes non-processed items into rest file.
     * Name of rest-file is inputfile-rest.csv
     *
     * It get's header from input file (as template for output).
     */
    private void writeRestToRestFile()
    {
        String ext = FilenameUtils.getExtension(inputFile);
        String outFile = FilenameUtils.removeExtension(inputFile) + "-rest." + ext;

        CsvWriter<I> writer = new CsvWriter<I>(outFile, inputFile, inputModelClass);
        try {
            writer.write(rest);
        } catch (Exception e) {
            log.error("Error during writing rest file: " + outFile, e);
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }

    private void cleanup()
    {
    }

    private void createNewGroup(I firstItem)
    {
        currentGroup = new CsvGroup<I>();
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

    private void addGroupToRest(CsvGroup<I> group)
    {
        rest.addAll(group.getItems());
    }

    /**
     * To generate values from incoming record into debug output
     *
     * @param id
     * @param bean
     */
    private void createDebugInfoFromIncomingRecord(Object id, I bean)
    {
        if (log.isDebugEnabled()) {
            StringWriter w = new StringWriter();
            CsvWriter<I> cw = new CsvWriter<I>(w, this.inputModelClass);
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
            log.debug("GROUP VALIDATION ERROR FROM " + source + ": " + message);
        }
    }

    protected boolean validateGroupLenght(CsvGroup<I> group, int expectedLen)
    {
        List<I> items = group.getItems();
        if (items.size() != expectedLen) {
            logValidationError(group.toString(), "Group length is not as expected: " + expectedLen);
            return false;
        }
        return true;
    }

    /**
     * To build a header line string from header column names from getOutputHeaderNames()
     *
     * @return header string
     */
    private Reader getHeaderReader()
    {
        if (outputHeaderReader == null) {
            StringBuilder b = new StringBuilder();
            String[] names = getOutputHeaderColumnNames();
            for (String name : names) {
                if (b.length() > 0) {
                    b.append(inputSeparator);
                }
                b.append(name);
            }
            String header = b.toString();
            outputHeaderReader = new StringReader(header);
        }
        return outputHeaderReader;
    }

    /**
     * If you want to create output file from input file.
     * 
     * @param inputFile
     * @return
     */
    protected static String createDefaultOutputFile(String inputFile)
    {
        if (inputFile == null) {
            return inputFile;
        }
        String ext = FilenameUtils.getExtension(inputFile);
        String base = FilenameUtils.removeExtension(inputFile);
        
        return base + DEFAULT_OUTPUT_FILE_POSTFIX + "." + ext;
    }
    
    /**
     * Return simple or composite ID of current bean.
     *
     * @param item
     * @return
     */
    protected abstract Object getGroupIdFromItem(I item);
    
    /**
     * It validates group.
     * If group not valid items will be sent into REST file.
     *
     *
     * @param group CSV record group
     * @return true: valid, false: invalid
     */
    protected abstract boolean validateGroup(CsvGroup<I> group);

    /**
     * Processing current group.
     *
     * @param group CSV record group
     * @return false: group rejected, write into REST file
     */
    protected abstract void processGroup(CsvGroup<I> group);

    /**
     * To specify (primary) output header fields which will be used by built-in output CSV writer.

     * @return
     */
    protected abstract String[] getOutputHeaderColumnNames();
}
