package org.ftoth.cvsproc.grp3;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class OutputModel
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

	@CsvBindByName(column = "Situation")
	@CsvBindByPosition(position = 3)
	private String situation;

	@CsvBindByName(column = "Local Currency")
	@CsvBindByPosition(position = 4)
	private String localCurrency;

	@CsvBindByName(column = "Reversal LC Amount")
	@CsvBindByPosition(position = 5)
	private double reversalLcAmount;

	@CsvBindByName(column = "Reversal USD Amount")
	@CsvBindByPosition(position = 6)
	private double reversalUsdAmount;

	@CsvBindByName(column = "Name")
	@CsvBindByPosition(position = 7)
	private String name;

	public String getOpenItemKey()
	{
		return openItemKey;
	}

	public void setOpenItemKey(String openItemKey)
	{
		this.openItemKey = openItemKey;
	}

	public String getCostCentre()
	{
		return costCentre;
	}

	public void setCostCentre(String costCentre)
	{
		this.costCentre = costCentre;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getSituation()
	{
		return situation;
	}

	public void setSituation(String situation)
	{
		this.situation = situation;
	}

	public String getLocalCurrency()
	{
		return localCurrency;
	}

	public void setLocalCurrency(String localCurrency)
	{
		this.localCurrency = localCurrency;
	}

	public double getReversalLcAmount()
	{
		return reversalLcAmount;
	}

	public void setReversalLcAmount(double reversalLcAmount)
	{
		this.reversalLcAmount = reversalLcAmount;
	}

	public double getReversalUsdAmount()
	{
		return reversalUsdAmount;
	}

	public void setReversalUsdAmount(double reversalUsdAmount)
	{
		this.reversalUsdAmount = reversalUsdAmount;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
