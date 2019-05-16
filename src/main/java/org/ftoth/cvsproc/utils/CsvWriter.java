package org.ftoth.cvsproc.utils;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.Writer;
import java.lang.reflect.ParameterizedType;
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
