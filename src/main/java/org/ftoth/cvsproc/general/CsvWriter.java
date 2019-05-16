package org.ftoth.cvsproc.general;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.Writer;
import java.util.List;

public class CsvWriter<T>
{
    StatefulBeanToCsv csvWriter;

    public CsvWriter(Writer writer)
    {
        this(writer, null);
    }

    public CsvWriter(Writer writer, ColumnPositionMappingStrategy<T> columnWritePositionStrategy)
    {
        csvWriter = new StatefulBeanToCsvBuilder(writer)
                .withMappingStrategy(columnWritePositionStrategy)
                .build();
    }

    public void write(List<T> beans) throws Exception
    {
        csvWriter.write(beans);
    }

    public void write(T bean) throws Exception
    {
        csvWriter.write(bean);
    }
}
