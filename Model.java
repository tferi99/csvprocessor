package org.ftoth.cvsproc.grp2;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class Model
{
	@CsvBindByName(column = "Open Item Key")
	@CsvBindByPosition(position = 0)
	private String openItemKey;

	@CsvBindByName(column = "Cost Centre")
	@CsvBindByPosition(position = 1)
	private String costCentre;

	@CsvBindByName(column = "Account")
	@CsvBindByPosition(position = 2)
	private String account;

	@CsvBindByName(column = "Account Description")
	@CsvBindByPosition(position = 3)
	private String accountDescription;

	@CsvBindByName(column = "Programme/Goal")
	@CsvBindByPosition(position = 4)
	private String programmeGoal;

	@CsvBindByName(column = "Situation")
	@CsvBindByPosition(position = 5)
	private String situation;

	@CsvBindByName(column = "Implementer")
	@CsvBindByPosition(position = 6)
	private String implementer;

	@CsvBindByName(column = "Book Code")
	@CsvBindByPosition(position = 7)
	private String bookCode;

	@CsvBindByName(column = "Local Currency")
	@CsvBindByPosition(position = 8)
	private String localCurrency;

	@CsvBindByName(column = "LC Amount")
	@CsvBindByPosition(position = 9)
	private String lcAmount;

	@CsvBindByName(column = "Reversal LC Amount")
	@CsvBindByPosition(position = 10)
	private String reversalLcAmount;

	@CsvBindByName(column = "USD Amount")
	@CsvBindByPosition(position = 11)
	private String usdAmount;

	@CsvBindByName(column = "Reversal USD Amount")
	@CsvBindByPosition(position = 12)
	private String reversalUsdAmount;

	@CsvBindByName(column = "Journal ID")
	@CsvBindByPosition(position = 13)
	private String journalId;

	@CsvBindByName(column = "Line Description")
	@CsvBindByPosition(position = 14)
	private String lineDescription;

	@CsvBindByName(column = "Line Reference")
	@CsvBindByPosition(position = 15)
	private String lineReference;

	@CsvBindByName(column = "Budget Year")
	@CsvBindByPosition(position = 16)
	private String budgetYear;

	@CsvBindByName(column = "Journal Date")
	@CsvBindByPosition(position = 17)
	private String journalDate;

	@CsvBindByName(column = "Fiscal Year")
	@CsvBindByPosition(position = 18)
	private String fiscalYear;

	@CsvBindByName(column = "Period/Month")
	@CsvBindByPosition(position = 19)
	private String periodMonth;

	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 20)
	private String status;

	@CsvBindByName(column = "Closed Date")
	@CsvBindByPosition(position = 21)
	private String closedDate;

	@CsvBindByName(column = "Source")
	@CsvBindByPosition(position = 22)
	private String source;

	@CsvBindByName(column = "Open Date")
	@CsvBindByPosition(position = 23)
	private String openDate;

	@CsvBindByName(column = "Reconcile Nbr")
	@CsvBindByPosition(position = 24)
	private String reconcileNbr;

	@CsvBindByName(column = "Ledger")
	@CsvBindByPosition(position = 25)
	private String ledger;

	@CsvBindByName(column = "Descr")
	@CsvBindByPosition(position = 26)
	private String descr;

	@CsvBindByName(column = "Name")
	@CsvBindByPosition(position = 27)
	private String name;

}
