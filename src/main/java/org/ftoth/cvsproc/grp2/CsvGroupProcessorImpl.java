package org.ftoth.cvsproc.grp2;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.general.CsvGroupProcessor;

import java.util.List;

public class CsvGroupProcessorImpl extends CsvGroupProcessor<Model>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessorImpl.class);

    public CsvGroupProcessorImpl(String inputFile) throws Exception
    {
        super(inputFile, new CustomMappingStrategyAll<Model>());
    }

    @Override
    protected Object getGroupIdFromBean(Model bean)
    {
        return bean.getOpenItemKey();
    }

    @Override
    protected boolean processGroup(List<Model> beans)
    {
        return true;
    }
}
