package org.ftoth.demo;

import com.opencsv.bean.HeaderIndex;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CustomColumnPositionMappingStrategy<T> extends com.opencsv.bean.ColumnPositionMappingStrategy
{
	public static void main(String[] args)
	{
		// annotated
		CustomColumnPositionMappingStrategy<Model> cm = new CustomColumnPositionMappingStrategy<Model>();
		test(cm, Model.class);

		// no name annot
		CustomColumnPositionMappingStrategy<ModelNoNameAnnot> cm2 = new CustomColumnPositionMappingStrategy<ModelNoNameAnnot>();
		test(cm2, ModelNoNameAnnot.class);

		// no position annot
		CustomColumnPositionMappingStrategy<ModelNoPosAnnot> cm3 = new CustomColumnPositionMappingStrategy<ModelNoPosAnnot>();
		test(cm3, ModelNoPosAnnot.class);

		// no annot
		CustomColumnPositionMappingStrategy<ModelNoAnnot> cm4 = new CustomColumnPositionMappingStrategy<ModelNoAnnot>();
		test(cm4, ModelNoAnnot.class);
	}

	private static void test(CustomColumnPositionMappingStrategy cm, Class modelClass)
	{
		String[] header = null;
		cm.setType(modelClass);
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
