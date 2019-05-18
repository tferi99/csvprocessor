package org.ftoth.cvsproc.general;

import com.opencsv.bean.CsvToBeanBuilder;
import org.ftoth.cvsproc.grp2.Model;
import org.ftoth.general.util.BeanDump;

import java.io.FileReader;
import java.util.List;

public class CsvReader<T>
{
    public static final char DEFAULT_SEPARATOR = ',';
    protected String inputFile;
    protected List<T> beans;

    public CsvReader(String inputFile) throws Exception
    {
        this(inputFile, DEFAULT_SEPARATOR);
    }

    /**
     * It creates a reader and it loads CSV file into it.
     *
     * @param inputFile
     * @param separator
     * @throws Exception
     */
    public CsvReader(String inputFile, char separator) throws Exception
    {
        this.inputFile = inputFile;
        FileReader rd = new FileReader(inputFile);
        try {
            beans = new CsvToBeanBuilder(rd).
                    withSeparator(separator).
                    withType(Model.class).
                    withSkipLines(1).                       // skip header
                    build().parse();
        }
        finally {
            rd.close();
        }
    }

    public String dump()
    {
        StringBuilder b = new StringBuilder();
        for (T bean: beans) {
            b.append(BeanDump.dump(bean));
        }
        return b.toString();
    }

    /**
     * To get loaded data beans.
     *
     * @return
     */
    public List<T> getBeans()
    {
        return beans;
    }
}
