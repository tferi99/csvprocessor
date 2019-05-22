package org.ftoth.cvsproc.grp2;

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

	@CsvBindByName(column = "USD Amount")
	@CsvBindByPosition(position = 4)
	private double usdAmount;

	@CsvBindByName(column = "Name")
	@CsvBindByPosition(position = 5)
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

	public double getUsdAmount()
	{
		return usdAmount;
	}

	public void setUsdAmount(double usdAmount)
	{
		this.usdAmount = usdAmount;
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
