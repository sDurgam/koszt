package ORClasses;

import java.util.ArrayList;
import java.util.Hashtable;

import Utils.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExpenseList2013 {

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getJanAmt() {
		return janAmt;
	}

	public void setJanAmt(Double janAmt) {
		this.janAmt = janAmt;
	}

	public Double getFebAmt() {
		return febAmt;
	}

	public void setFebAmt(Double febAmt) {
		this.febAmt = febAmt;
	}

	public Double getMarAmt() {
		return marAmt;
	}

	public void setMarAmt(Double marAmt) {
		this.marAmt = marAmt;
	}

	public Double getAprAmt() {
		return aprAmt;
	}

	public void setAprAmt(Double aprAmt) {
		this.aprAmt = aprAmt;
	}

	public Double getMayAmt() {
		return mayAmt;
	}

	public void setMayAmt(Double mayAmt) {
		this.mayAmt = mayAmt;
	}

	public Double getJunAmt() {
		return junAmt;
	}

	public void setJunAmt(Double junAmt) {
		this.junAmt = junAmt;
	}

	public Double getJulAmt() {
		return julAmt;
	}

	public void setJulAmt(Double julAmt) {
		this.julAmt = julAmt;
	}

	public Double getAugAmt() {
		return augAmt;
	}

	public void setAugAmt(Double augAmt) {
		this.augAmt = augAmt;
	}

	public Double getSepAmt() {
		return sepAmt;
	}

	public void setSepAmt(Double sepAmt) {
		this.sepAmt = sepAmt;
	}

	public Double getOctAmt() {
		return octAmt;
	}

	public void setOctAmt(Double octAmt) {
		this.octAmt = octAmt;
	}

	public Double getNovAmt() {
		return novAmt;
	}

	public void setNovAmt(Double novAmt) {
		this.novAmt = novAmt;
	}

	public Double getDecAmt() {
		return decAmt;
	}

	public void setDecAmt(Double decAmt) {
		this.decAmt = decAmt;
	}

	String category;
	Double janAmt;
	Double febAmt;
	Double marAmt;
	Double aprAmt;
	Double mayAmt;
	Double junAmt;
	Double julAmt;
	Double augAmt;
	Double sepAmt;
	Double octAmt;
	Double novAmt;
	Double decAmt;
	
	public ExpenseList2013() {
		// TODO Auto-generated constructor stub
	
	}
	
	public void setMonAmt(String mon, Double amt)
	{
		if(mon == Utils.AppConstants.jan_month)
		{
			setJanAmt(amt);
		}
		if(mon == Utils.AppConstants.feb_month)
		{
			setFebAmt(amt);
		}
		if(mon == Utils.AppConstants.mar_month)
		{
			setMarAmt(amt);
		}
		if(mon == Utils.AppConstants.apr_month)
		{
			setAprAmt(amt);
		}
		if(mon == Utils.AppConstants.may_month)
		{
			setMayAmt(amt);
		}
		if(mon == Utils.AppConstants.jun_month)
		{
			setJunAmt(amt);
		}
		if(mon == Utils.AppConstants.jul_month)
		{
			setJulAmt(amt);
		}
		if(mon == Utils.AppConstants.aug_month)
		{
			setAugAmt(amt);
		}
		if(mon == Utils.AppConstants.sep_month)
		{
			setSepAmt(amt);
		}
		if(mon == Utils.AppConstants.oct_month)
		{
			setOctAmt(amt);
		}
		if(mon == Utils.AppConstants.nov_month)
		{
			setNovAmt(amt);
		}
		if(mon == Utils.AppConstants.dec_month)
		{
			setDecAmt(amt);
		}

	}
	
	public Double getMonAmt(String mon)
	{
		if(mon == Utils.AppConstants.jan_month)
		{
			return getJanAmt();
		}
		if(mon == Utils.AppConstants.feb_month)
		{
			return getFebAmt();
		}
		if(mon == Utils.AppConstants.mar_month)
		{
			return getMarAmt();
		}
		if(mon == Utils.AppConstants.apr_month)
		{
			return getAprAmt();
		}
		if(mon == Utils.AppConstants.may_month)
		{
			return getMayAmt();
		}
		if(mon == Utils.AppConstants.jun_month)
		{
			return getJunAmt();
		}
		if(mon == Utils.AppConstants.jul_month)
		{
			return getJulAmt();
		}
		if(mon == Utils.AppConstants.aug_month)
		{
			return getAugAmt();
		}
		if(mon == Utils.AppConstants.sep_month)
		{
			return getSepAmt();
		}
		if(mon == Utils.AppConstants.oct_month)
		{
			return getOctAmt();
		}
		if(mon == Utils.AppConstants.nov_month)
		{
			return getNovAmt();
		}
		if(mon == Utils.AppConstants.dec_month)
		{
			return getDecAmt();
		}
		return 0.0;
	}
	
	//UpdateExpenseList Table
		public void UpdateExpenseList(SQLiteDBHelper sqlHelper, String catName, String monName, Double amount)
		{
			Double dbAmt = GetExpenseListCatAmt(sqlHelper, catName, monName) + amount;
			SQLiteDatabase dbWriter = sqlHelper.getWritableDatabase();	// TODO Auto-generated method stub
			ContentValues value = new ContentValues();
			value.put(monName, dbAmt);
			catName = "\'" + catName + "\'";
			String whereClause = sqlHelper.EXPENSELIST_CATEGORY + "= "  + catName;
			dbWriter.update(sqlHelper.TABLE_EXPENSELIST2013, value, whereClause, null);
			dbWriter.close();
		}
		
		
		private Double GetExpenseListCatAmt(SQLiteDBHelper sqlHelper, String catName, String mon)
		{
			SQLiteDatabase dbReader = sqlHelper.getReadableDatabase();String[] allColumns = {mon};
			String[] catNameString = new String[] { catName };
			Cursor getExpenseListCatAmtCursor =  dbReader.query(sqlHelper.TABLE_EXPENSELIST2013, allColumns, "category = ?", catNameString, null, null, null, null);
			if(getExpenseListCatAmtCursor.getCount() == 1)	//show all stores to category
			{	
				getExpenseListCatAmtCursor.moveToFirst();
				return Double.valueOf(getExpenseListCatAmtCursor.getString(0));
			}
			return 0.0;
		}
		
		
		public Hashtable<String, Double> GetMontlyExpenses(
				SQLiteDBHelper sqlHelper, String[] cols, String month) {
			// TODO Auto-generated method stub
			Hashtable<String, Double> monthExpenses = new Hashtable<String, Double>();
			SQLiteDatabase dbReader = sqlHelper.getReadableDatabase();
			String whereCaluse = month + " > " + 0.0;
			Cursor getExpenseListCatAmtCursor =  dbReader.query(sqlHelper.TABLE_EXPENSELIST2013, cols, month + " > ?", new String[] {"0.0"}, null, null, null);
			int catCount = getExpenseListCatAmtCursor.getCount();
			if(catCount != 0)
			{
				getExpenseListCatAmtCursor.moveToFirst();
				do
				{
					monthExpenses.put(getExpenseListCatAmtCursor.getString(0), getExpenseListCatAmtCursor.getDouble(1));
				}while(getExpenseListCatAmtCursor.moveToNext());
			}
			getExpenseListCatAmtCursor.close();
			dbReader.close();
			return monthExpenses;
		}

}
