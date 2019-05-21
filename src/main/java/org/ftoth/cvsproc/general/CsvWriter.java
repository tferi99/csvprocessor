package org.ftoth.cvsproc.general;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class CsvWriter<T>
{
    private static Logger log = Logger.getLogger(Logger.class);

    Writer writer;
    protected Class<T> modelClass;
    StatefulBeanToCsv csvWriter = null;

    public CsvWriter(Writer writer, Class<T> modelClass)
    {
        this(writer, modelClass, null);
    }

    public CsvWriter(Writer writer, Class<T> modelClass, ColumnPositionMappingStrategy<T> columnWritePositionStrategy)
    {
        this.writer = writer;
        this.modelClass = modelClass;
        csvWriter = new StatefulBeanToCsvBuilder(writer)
                .withMappingStrategy(columnWritePositionStrategy)
                .build();
    }

    public CsvWriter(String outputFile, String outputFileTemplate, Class<T> outputModelClass)
    {
        Reader headerRd = null;
        CSVReader headerCsvRd = null;
        try {
            headerRd = new FileReader(outputFileTemplate);
            headerCsvRd = new CSVReader(headerRd);
        }
        catch (Exception e) {
            log.error("Error during reading CVS output template: " + outputFileTemplate, e);
            e.printStackTrace();
        }


        try {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<T>();
            strategy.setType(outputModelClass);
            strategy.captureHeader(headerCsvRd);

            this.writer = new FileWriter(outputFile);
            csvWriter = new StatefulBeanToCsvBuilder(writer)
                    .withApplyQuotesToAll(false)
                    .withMappingStrategy(strategy)
                    .build();
        }
        catch (Exception e) {
            log.error("Error during writing CVS: " + outputFile, e);
            e.printStackTrace();
        }
        finally {
            if (headerRd != null) {
                try {
                    headerRd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write(List<T> beans) throws Exception
    {
        csvWriter.write(beans);
    }

    public void write(T bean) throws Exception
    {
        csvWriter.write(bean);
    }

    public void close()
    {
        if (writer  != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
