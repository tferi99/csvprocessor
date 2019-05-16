package org.ftoth.cvsproc.grp2;

import com.opencsv.bean.ColumnPositionMappingStrategy;

public class CustomMappingStrategyAll<Model> extends ColumnPositionMappingStrategy<Model>
{
	public static final String[] HEADER = new String[] {
		"Open Item Key", "Cost Centre", "Account", "Account Description", "Programme/Goal", "Situation", "Implementer", "Book Code", "Local Currency", "LC Amount", "Reversal LC Amount", "USD Amount", "Reversal USD Amount", "Journal ID", "Line Description", "Line Reference", "Budget Year", "Journal Date", "Fiscal Year", "Period/Month", "Status", "Closed Date", "Source", "Open Date", "Reconcile Nbr", "Ledger", "Descr", "Name"
	};

	@Override
	public String[] generateHeader(Model bean) {
		return HEADER;
	}
}
