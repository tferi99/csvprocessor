package org.ftoth.demo;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderIndex;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.ftoth.cvsproc.grp2.Model;

public class CustomHeaderColumnNameMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T>
{
    public static void main(String[] args)
    {
        String[] header = null;
        CustomHeaderColumnNameMappingStrategy<Model> cm = new CustomHeaderColumnNameMappingStrategy<Model>();
        cm.setType(Model.class);
        try {
            header = cm.generateHeader(new Model());
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

        HeaderIndex hi = cm.headerIndex;
        String p5 = hi.getByPosition(5);
        String[] hidx = hi.getHeaderIndex();
        int len = hi.getHeaderIndexLength();

        System.out.println("aaaa");
    }
}

