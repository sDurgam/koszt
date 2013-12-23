package ORClasses;

import java.util.ArrayList;

import Utils.Helper;
import Utils.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Stores {
	
	String _name;
	String _category;
	
	public Stores()
	{
		// TODO Auto-generated constructor stub
	}
	public String get_name() {
		return _name;
	}

	protected void set_name(String _name) {
		this._name = _name;
	}

	public String get_category() {
		return _category;
	}

	protected void set_category(String _category) {
		this._category = _category;
	}
	
	//Get existing stores from DB
	public ArrayList<Stores> GetExistingStoresDB(SQLiteDBHelper sqlHelper, ArrayList<String> storeNames)
	{
		ArrayList<Stores> stores = new ArrayList<Stores>();
		Stores store;
		int catCnt;
		String[] storeCol = { SQLiteDBHelper.STORE_NAME, SQLiteDBHelper.STORE_CATEGORY };
		String whereClause = SQLiteDBHelper.STORE_NAME + " IN (" + Helper.makePlaceholders(storeNames.size(), storeNames) + ")";
		SQLiteDatabase dbReader = sqlHelper.getReadableDatabase();
		
		//String whereClause = SQLiteDBHelper.EXPENSELIST_JAN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_FEB + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAR + "!= 0" + "  OR " + SQLiteDBHelper.EXPENSELIST_APR + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_MAY + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUN + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_JUL + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_AUG + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_SEP + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_OCT + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_NOV + "!= 0" + " OR " + SQLiteDBHelper.EXPENSELIST_DEC + "!= 0";
		Cursor getStoresCursor = dbReader.query(SQLiteDBHelper.TABLE_STORES, storeCol, whereClause, null, null, null, null, null);
		catCnt = getStoresCursor.getCount();
		if(catCnt != 0)
		{
			getStoresCursor.moveToFirst();
			do
			{
				store = new Stores();
				store.set_name( getStoresCursor.getString(0));
				store.set_category( getStoresCursor.getString(1));
			}while(getStoresCursor.moveToNext());
		}
		getStoresCursor.close();
		dbReader.close();
		return stores;
	}
	

	//Update stores categories to DB
	public void UpdateStoreCategories(SQLiteDBHelper sqlHelper, ArrayList<ContentValues> stores)
	{
		SQLiteDatabase dbWriter = sqlHelper.getWritableDatabase();	// TODO Auto-generated method stub
		dbWriter.beginTransaction();		
		for(ContentValues store : stores)
		{
			dbWriter.insertOrThrow(sqlHelper.TABLE_STORES, null, store);
		}
		dbWriter.setTransactionSuccessful();
		dbWriter.endTransaction();
		dbWriter.close();
	}

}
