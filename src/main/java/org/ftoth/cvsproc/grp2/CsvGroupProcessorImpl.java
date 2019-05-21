package org.ftoth.cvsproc.grp2;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.general.CsvGroup;
import org.ftoth.cvsproc.general.CsvGroupProcessor;

public class CsvGroupProcessorImpl extends CsvGroupProcessor<InputModel>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessorImpl.class);

    public CsvGroupProcessorImpl(String inputFile) throws Exception
    {
        super(inputFile, InputModel.class);
    }

    @Override
    protected Object getGroupIdFromItem(InputModel bean)
    {
        return bean.getOpenItemKey();
    }

    @Override
    protected boolean validateGroup(CsvGroup<InputModel> group)
    {
        /*
        // random error
        if (Math.random() > 0.5) {
            logValidationError(this.getClass().getSimpleName(), "test issue");
            return false;
        }
        return true;*/
        return false;
    }

    @Override
    protected void processGroup(CsvGroup<InputModel> group)
    {

    }
}
