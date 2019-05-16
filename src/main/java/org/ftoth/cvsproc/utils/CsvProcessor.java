package org.ftoth.cvsproc.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import org.ftoth.cvsproc.grp2.Model;
import org.ftoth.general.util.BeanDump;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

public class CsvProcessor<T>
{
    public static final char DEFAULT_SEPARATOR = ',';
    protected String inputFile;
    protected List<T> beans;

    public CsvProcessor(String inputFile) throws Exception
    {
        this(inputFile, DEFAULT_SEPARATOR);
    }

    public CsvProcessor(String inputFile, char separator) throws Exception
    {
        this.inputFile = inputFile;
        beans = new CsvToBeanBuilder(new FileReader(inputFile)).
                withSeparator(separator).
                withType(Model.class).
                withSkipLines(1).                       // skip header
                build().parse();
    }

    public String dump()
    {
        StringBuilder b = new StringBuilder();
        for (T bean: beans) {
            b.append(BeanDump.dump(bean));
        }
        return b.toString();
    }

    public List<T> getBeans()
    {
        return beans;
    }
}
