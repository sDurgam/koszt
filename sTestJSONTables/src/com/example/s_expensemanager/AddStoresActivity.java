package com.example.s_expensemanager;

import java.util.ArrayList;

import Utils.SQLiteDBHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddStoresActivity extends Activity implements OnClickListener {

	SQLiteDBHelper helper;
	EditText storeNameTxt;
	Spinner storeCategoriesSpinner;
	Button addStoreBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addstores);
		storeNameTxt = (EditText) findViewById(R.id.storeNametxt);
		storeCategoriesSpinner = (Spinner) findViewById(R.id.categoriesSpinner);
		addStoreBtn = (Button) findViewById(R.id.addStoreBtn);
		addStoreBtn.setOnClickListener(this);
		//storeCategoriesList.setOnItemSelectedListener(listener);
		helper = new SQLiteDBHelper(this);
		PopulateCategoriesSpinner();
	}

	private void PopulateCategoriesSpinner()
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor categoriesCur = db.rawQuery("select name from categories", null);
		ArrayList<String> categoriesArrayList = new ArrayList<String>();
		if(categoriesCur.moveToFirst())
		{
			do 
			{
				categoriesArrayList.add(categoriesCur.getString(0));
			}while(categoriesCur.moveToNext());
		}
		categoriesCur.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesArrayList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		storeCategoriesSpinner.setAdapter(adapter);

//		SimpleCursorAdapter curAdapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesCur, new String[] {"_id"}, new int[] {android.R.id.text1});
//		curAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		storeCategoriesList.setAdapter(curAdapter);
		
		db.close();
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == addStoreBtn)
		{
			String selectedCategory;
			String storeName;
			SQLiteDatabase dbWriter = helper.getWritableDatabase();
			selectedCategory = storeCategoriesSpinner.getSelectedItem().toString();
			storeName = storeNameTxt.getText().toString();
			ContentValues val = new ContentValues();
			val.put(SQLiteDBHelper.STORE_NAME, storeName);
			val.put(SQLiteDBHelper.STORE_CATEGORY, selectedCategory);
			long isSuccess = dbWriter.insertOrThrow(SQLiteDBHelper.TABLE_STORES, null, val);
			if(isSuccess != -1)
			{
				Toast.makeText(this, "Successfully added entry to the database", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "Could not add entry to the database. Entry may already exist", Toast.LENGTH_LONG).show();
			}
			dbWriter.close();
			helper.close();
		}
		
	}
}