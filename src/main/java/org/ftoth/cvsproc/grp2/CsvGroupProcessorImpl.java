package org.ftoth.cvsproc.grp2;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.general.CsvGroup;
import org.ftoth.cvsproc.general.CsvGroupProcessor;
import org.ftoth.demo.CustomColumnPositionMappingStrategy;

public class CsvGroupProcessorImpl extends CsvGroupProcessor<Model>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessorImpl.class);

    public CsvGroupProcessorImpl(String inputFile) throws Exception
    {
        super(inputFile, new CustomColumnPositionMappingStrategy<Model>());
    }

    @Override
    protected Object getGroupIdFromItem(Model bean)
    {
        return bean.getOpenItemKey();
    }

    @Override
    protected boolean validateGroup(CsvGroup<Model> group)
    {
        //
        if (Math.random() > 0.5) {
            logValidationError(this.getClass().getSimpleName(), "test issue");
            return false;
        }
        return true;
    }

    @Override
    protected void processGroup(CsvGroup<Model> group)
    {

    }
}
