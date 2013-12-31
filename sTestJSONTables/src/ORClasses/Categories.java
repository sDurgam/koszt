package ORClasses;

import java.util.ArrayList;

import Utils.SQLiteDBHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Categories {

	int _id;
	String _name;
	
	// Empty constructor
	public  Categories() {
	}

	// constructor
	public Categories(int id, String name) {
		this._id = id;
		this._name = name;
	}

	// constructor
	public Categories(String name) {
		this._name = name;
	}
	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}
	
	public ArrayList<ExpenseList2013> GetCategoryExpenses(SQLiteDBHelper sqlHelper, String tabName, String[] cols)
	{
		SQLiteDatabase dbWriter = sqlHelper.getWritableDatabase();
		ArrayList<ExpenseList2013> expenseList2013List = new ArrayList<ExpenseList2013>();
		ExpenseList2013 expense;
		//String selection = SQLiteDBHelper.EXPENSELIST_JAN + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_FEB + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_MAR + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_APR + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_MAY + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_JUN + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_JUL + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_AUG + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_SEP + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_OCT + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_NOV + " > ?" + " OR " + SQLiteDBHelper.EXPENSELIST_DEC + " > ?";
		//select column_name, category, sep from expenselist where column_name = sep
		String whereClause = SQLiteDBHelper.EXPENSELIST_JAN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_FEB + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAR + "!= 0" + "  OR " + SQLiteDBHelper.EXPENSELIST_APR + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAY + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUL + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_AUG + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_SEP + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_OCT + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_NOV + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_DEC + "!= 0";
		Cursor CategoryValuesCursor = dbWriter.query(tabName, cols, whereClause, null, null, null, null, null);
		int catCount = CategoryValuesCursor.getCount();
		if(catCount != 0)
		{
			CategoryValuesCursor.moveToFirst();
			do
			{
				expense = new ExpenseList2013();
				expense.setCategory(CategoryValuesCursor.getString(0));
				expense.setJanAmt(CategoryValuesCursor.getDouble(1));
				expense.setFebAmt(CategoryValuesCursor.getDouble(2));
				expense.setMarAmt(CategoryValuesCursor.getDouble(3));
				expense.setAprAmt(CategoryValuesCursor.getDouble(4));
				expense.setMayAmt(CategoryValuesCursor.getDouble(5));
				expense.setJunAmt(CategoryValuesCursor.getDouble(6));
				expense.setJulAmt(CategoryValuesCursor.getDouble(7));
				expense.setAugAmt(CategoryValuesCursor.getDouble(8));
				expense.setSepAmt(CategoryValuesCursor.getDouble(9));
				expense.setOctAmt(CategoryValuesCursor.getDouble(10));
				expense.setNovAmt(CategoryValuesCursor.getDouble(11));
				expense.setDecAmt(CategoryValuesCursor.getDouble(12));
				expenseList2013List.add(expense);
			}while(CategoryValuesCursor.moveToNext());
		}
		CategoryValuesCursor.close();
		dbWriter.close();
		return expenseList2013List;
	}
	
	public String[] GetExistingCategoriesDB(SQLiteDBHelper sqlHelper)
	{
		String[] categories = null;
		int catCnt;
		int index = 0;
		String[] catCol = { SQLiteDBHelper.EXPENSELIST_CATEGORY };
		SQLiteDatabase dbReader = sqlHelper.getReadableDatabase();
		String whereClause = SQLiteDBHelper.EXPENSELIST_JAN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_FEB + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAR + "!= 0" + "  OR " + SQLiteDBHelper.EXPENSELIST_APR + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAY + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUL + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_AUG + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_SEP + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_OCT + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_NOV + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_DEC + "!= 0";
		Cursor getCategoriesCursor = dbReader.query(SQLiteDBHelper.TABLE_EXPENSELIST2013, catCol, whereClause, null, null, null, null, null);
		catCnt = getCategoriesCursor.getCount();
		if(catCnt != 0)
		{
			categories = new String[catCnt];
			getCategoriesCursor.moveToFirst();
			do
			{
				categories[index ++] = getCategoriesCursor.getString(0);
			}while(getCategoriesCursor.moveToNext());
		}
		getCategoriesCursor.close();
		dbReader.close();
		return categories;
	}


}
