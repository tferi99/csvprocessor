package org.ftoth.cvsproc.grp3;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.general.CsvGroup;
import org.ftoth.cvsproc.general.CsvGroupProcessor;
import org.ftoth.general.util.MathUtil;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvGroupProcessorImpl extends CsvGroupProcessor<InputModel, OutputModel>
{
    private static Logger log = Logger.getLogger(CsvGroupProcessorImpl.class);
    private static final int VALID_GRP_LEN = 3;

    private static final String[] OUTPUTHEADER = {
            "Open Item Key",
            "Cost Centre",
            "Account",
            "Situation",
            "Local Currency",
            "Reversal LC Amount",
            "Reversal USD Amount",
            "Name"
    };

    // USD1
    private InputModel usd0;
    // USD2
    private InputModel usd1;
    // other
    private InputModel other;

    public CsvGroupProcessorImpl(String inputFile) throws Exception
    {
        super(inputFile, InputModel.class, createDefaultOutputFile(inputFile), OutputModel.class);
    }

    public CsvGroupProcessorImpl(String inputFile, String outputFile) throws Exception
    {
        super(inputFile, InputModel.class, outputFile, OutputModel.class);
    }

    @Override
    protected Object getGroupIdFromItem(InputModel bean)
    {
        return bean.getOpenItemKey();
    }

    @Override
    protected boolean validateGroup(CsvGroup<InputModel> group)
    {
        //return false;

        List<InputModel> items = group.getItems();

        if (!validateGroupLenght(group, VALID_GRP_LEN)) {
            return false;
        }

        // identify items by Local Currency
        InputModel[] im = new InputModel[3];
        int usdNum = 0;
        for (InputModel item : group.getItems()) {
            if (item.getLocalCurrency().equals("USD")) {
                im[usdNum] = item;
                usdNum++;
            }
            else {
                im[2] = item;
            }
        }
        if (usdNum != 2) {
            logValidationError(group.toString(), "Group does not contain 2 USD items (by Local Currency)");
            return false;
        }
        usd0 = im[0];
        usd1 = im[1];
        other = im[2];

        // checking other amount
        if (other.getUsdAmount() < 0) {
            logValidationError(group.toString(), "USD Amount of other currency should be greater than 0)");
            return false;
        }

        return true;
    }

    @Override
    protected String[] getOutputHeaderColumnNames() {
        return OUTPUTHEADER;
    }

    @Override
    protected void processGroup(CsvGroup<InputModel> group)
    {
        // USD out
        OutputModel out = new OutputModel();
        mapUnchangedValues(usd0, out);
        out.setLocalCurrency(usd0.getLocalCurrency());
        double revLcSum = usd0.getReversalLcAmount() + usd1.getReversalLcAmount();
        double revUsdSum = usd0.getReversalUsdAmount() + usd1.getReversalUsdAmount();
        out.setReversalLcAmount(fixDouble(revLcSum, 2));
        out.setReversalUsdAmount(fixDouble(revUsdSum, 2));
        result.add(out);

        // other currency
        out = new OutputModel();
        mapUnchangedValues(usd0, out);
        out.setLocalCurrency(other.getLocalCurrency());
        out.setReversalLcAmount(other.getReversalLcAmount());
        out.setReversalUsdAmount(other.getReversalUsdAmount());
        result.add(out);
    }

    private void mapUnchangedValues(InputModel in, OutputModel out)
    {
        out.setOpenItemKey(in.getOpenItemKey());
        out.setCostCentre(in.getCostCentre());
        out.setAccount(in.getAccount());
        out.setSituation(in.getSituation());
        out.setName(in.getName());
    }

}
