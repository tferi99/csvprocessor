package org.ftoth.cvsproc.utils;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.apache.log4j.Logger;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvGroupProcessor<T>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessor.class);

    CsvProcessor<T> processor;
    protected List<List<T>> groups;
    protected List<T> currentGroup = null;
    Object currentGroupId;
    ColumnPositionMappingStrategy<T> columnWritePositionStrategy = null;

    public CsvGroupProcessor(String inputFile, ColumnPositionMappingStrategy<T> columnWritePositionStrategy) throws Exception
    {
        this.columnWritePositionStrategy = columnWritePositionStrategy;
        processor = new CsvProcessor<T>(inputFile);
    }

    public void process()
    {
        Object prevId = null;

        List<T> beans = processor.getBeans();
        for (T bean: beans) {
            Object currentId = getGroupIdFromBean(bean);

            // on first data row
            if (currentGroup == null) {
                createNewGroup(bean);
                prevId = currentId;
                debugIncomingBean(currentId, bean);
                continue;
            }

            // identifying group
            if (prevId.equals(currentId)) {
                // add to current group
                currentGroup.add(bean);
            }
            else {
                onGroupLoaded();                    // precess last group
                createNewGroup(bean);               // create a new group
            }
            debugIncomingBean(currentId, bean);
            prevId = currentId;
        }
        // not processed, last group
        if (beans.size() > 0) {
            onGroupLoaded();
        }
    }

    private void createNewGroup(T firstItem)
    {
        if (log.isDebugEnabled()) {
            currentGroupId = getGroupIdFromBean(firstItem);
            log.debug("-------------------------------------- new group[" + currentGroupId + "] -------------------------------------------------------------");
        }
        currentGroup = new ArrayList<T>();
        currentGroup.add(firstItem);
    }

    private void onGroupLoaded()
    {
        if (log.isDebugEnabled()) {
            log.debug("Processing of group[" + currentGroupId + "] - size: " + currentGroup.size());
        }
        if (!processGroup(currentGroup)) {
            /// write into rest
        }
    }

    private void debugIncomingBean(Object id, T bean)
    {
        if (log.isDebugEnabled()) {
            StringWriter w = new StringWriter();
            CsvWriter<T> cw = new CsvWriter<T>(w);
            try {
                cw.write(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String s = w.toString();
            if (s.endsWith("\n")) {
                s = s.substring(0, s.length() - 1);
            }
            log.debug("IN record[" + id + "] [" + s + "]");
        }
    }

    /**
     * Return simple or composite ID of current bean.
     *
     * @param bean
     * @return
     */
    protected abstract Object getGroupIdFromBean(T bean);


    /**
     *
     * @param beans
     */

    /**
     * Processing current group.
     *
     * @param beans group elements
     * @return false: group rejected, write into REST file
     */
    protected abstract boolean processGroup(List<T> beans);
}
