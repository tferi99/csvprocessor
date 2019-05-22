package org.ftoth.cvsproc.grp2;

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
    private static final int VALID_GRP_LEN = 2;

    private static final String[] OUTPUTHEADER = {
            "Open Item Key",
            "Cost Centre",
            "Account",
            "Situation",
            "USD Amount",
            "Name"
    };

    // early item
    private InputModel i0;
    // late item
    private InputModel i1;

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

        // null-validation
        List<InputModel> validated = items.stream()
                .filter(i -> Objects.nonNull(i.getJournalDate()))
                .collect(Collectors.toList());
        if (validated.size() != VALID_GRP_LEN) {
            logValidationError(group.toString(), "Null value found (JournalDate)");
            return false;
        }

        // identify items by journal date
        i0 = items.get(0);
        i1 = items.get(1);
        if (i0.getJournalDate().getTime() > i1.getJournalDate().getTime()) {
            // replace them
            InputModel tmp = i0;
            i0 = i1;
            i1 = tmp;
        }

        // validating USD amounts
        if (i0.getUsdAmount() > 0) {
            logValidationError(group.toString(), "USD Amount of earlier item is positive");
            return false;
        }
        if (i1.getUsdAmount() < 0) {
            logValidationError(group.toString(), "USD Amount of later item is negative");
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
        InputModel in1 = group.getItems().get(0);        // all items almost contain the same, the 1st is good enough
        OutputModel out = new OutputModel();

        // populating output (mapping in most cases)
        out.setOpenItemKey(in1.getOpenItemKey());
        out.setCostCentre(in1.getCostCentre());
        out.setAccount(in1.getAccount());
        out.setSituation(in1.getSituation());
        out.setName(in1.getName());

        InputModel in2 = group.getItems().get(1);
        double usd1 = in1.getUsdAmount();
        double usd2 = in2.getUsdAmount();
        double diff = (usd1 + usd2) * -1;
        double fixedDiff = MathUtil.round(diff, 2);
        /*String d = String.format("%.2f", fixedDiff);
        out.setUsdAmount(d);*/
        out.setUsdAmount(fixedDiff);

        result.add(out);
    }
}
