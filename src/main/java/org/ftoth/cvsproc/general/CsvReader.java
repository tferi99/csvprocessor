package org.ftoth.cvsproc.general;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.log4j.Logger;
import org.ftoth.general.util.BeanDump;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReader<T>
{
    private static Logger log = Logger.getLogger(CsvReader.class);

    public static final char DEFAULT_SEPARATOR = ',';
    protected String inputFile;
    protected List<T> beans;
    protected Class<T> inputModelClass;

    public CsvReader(String inputFile, Class<T> sourceModelClass) throws Exception
    {
        this(inputFile, sourceModelClass, DEFAULT_SEPARATOR);
    }

    /**
     * It creates a reader and it loads CSV file into it.
     *
     * @param inputFile
     * @param separator
     * @throws Exception
     */
    public CsvReader(String inputFile, Class<T> inputModelClass, char separator)
    {
        this.inputFile = inputFile;
        this.inputModelClass = inputModelClass;
        FileReader rd = null;
        try {
            rd = new FileReader(inputFile);
            beans = new CsvToBeanBuilder(rd).
                    withType(inputModelClass).                  // it should be named and positioned
                    withSeparator(separator).
                    withSkipLines(1).                       // skip header
                    build().parse();
        }
        catch (Exception e) {
            log.error("Error during reading CVS: " + inputFile, e);
            e.printStackTrace();
        }
        finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
