package org.ftoth.cvsproc.grp2;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class InputModel
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
	private double usdAmount;

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
	@CsvDate("dd/MM/yyyy")
	private Date journalDate;

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

	@CsvBindByName(column = "LastName")
	@CsvBindByPosition(position = 28)
	private String lastname;

	public String getOpenItemKey() {
		return openItemKey;
	}

	public void setOpenItemKey(String openItemKey) {
		this.openItemKey = openItemKey;
	}

	public String getCostCentre() {
		return costCentre;
	}

	public void setCostCentre(String costCentre) {
		this.costCentre = costCentre;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	public String getProgrammeGoal() {
		return programmeGoal;
	}

	public void setProgrammeGoal(String programmeGoal) {
		this.programmeGoal = programmeGoal;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getImplementer() {
		return implementer;
	}

	public void setImplementer(String implementer) {
		this.implementer = implementer;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getLocalCurrency() {
		return localCurrency;
	}

	public void setLocalCurrency(String localCurrency) {
		this.localCurrency = localCurrency;
	}

	public String getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(String lcAmount) {
		this.lcAmount = lcAmount;
	}

	public String getReversalLcAmount() {
		return reversalLcAmount;
	}

	public void setReversalLcAmount(String reversalLcAmount) {
		this.reversalLcAmount = reversalLcAmount;
	}

	public double getUsdAmount() {
		return usdAmount;
	}

	public void setUsdAmount(double usdAmount) {
		this.usdAmount = usdAmount;
	}

	public String getReversalUsdAmount() {
		return reversalUsdAmount;
	}

	public void setReversalUsdAmount(String reversalUsdAmount) {
		this.reversalUsdAmount = reversalUsdAmount;
	}

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	public String getLineReference() {
		return lineReference;
	}

	public void setLineReference(String lineReference) {
		this.lineReference = lineReference;
	}

	public String getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getReconcileNbr() {
		return reconcileNbr;
	}

	public void setReconcileNbr(String reconcileNbr) {
		this.reconcileNbr = reconcileNbr;
	}

	public String getLedger() {
		return ledger;
	}

	public void setLedger(String ledger) {
		this.ledger = ledger;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
