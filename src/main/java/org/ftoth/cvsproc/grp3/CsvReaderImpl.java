package org.ftoth.cvsproc.grp3;

import org.ftoth.cvsproc.general.CsvReader;

public class CsvReaderImpl extends CsvReader<InputModel>
{
    public CsvReaderImpl(String inputFile) throws Exception {
        super(inputFile, InputModel.class);
    }
}
