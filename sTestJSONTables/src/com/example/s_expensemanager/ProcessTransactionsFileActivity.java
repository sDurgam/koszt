package com.example.s_expensemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CustomAdapters.CustomAdapter;
import ORClasses.ExpenseList2013;
import ORClasses.Stores;
import ORClasses.Transaction;
import Utils.SQLiteDBHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.Graph.DisplayChartsActivity;
import com.example.s_expensemanager.R;



public class ProcessTransactionsFileActivity extends Activity implements OnClickListener
{
	Button addStoreToDBBtn;
	ListView transcationsListView;
	TextView chooseCatDisp;
	Spinner catSpinner;
	SQLiteDBHelper sqlHelper;
	ArrayList<String> existingStores;
	ArrayList<String> existingCategories;
	ArrayList<String> storeNamesList;
	ArrayList<Stores> dbStoresList; 
	ArrayList<String> newStoresArray;
	int MAX_COUNT = 5;
	
	double totalExpenses = 0;
	String[] lines;
	Hashtable<String, ArrayList<String []>> StoresDateAmtTable;
	Stores storesObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytransactions);
		InitializeArrays();
		InitializeDBClasses();
		InitializeViewItems();
		InitializeClickHandlers();
		storesObj = new Stores();
		lines = new String[this.getIntent().getStringArrayExtra("stores").length];
		lines = this.getIntent().getStringArrayExtra("stores");
		try {
			ReadFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo)
	{
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	private void InitializeArrays()
	{	
		storeNamesList = new ArrayList<String>();
		newStoresArray = new ArrayList<String>();
		existingStores = new ArrayList<String>();
		existingCategories = new ArrayList<String>();
		StoresDateAmtTable = new Hashtable<String, ArrayList<String[]>>();
	}

	private void InitializeDBClasses()
	{	
		sqlHelper = new SQLiteDBHelper(this);
	}

	private void InitializeViewItems()
	{
		transcationsListView = (ListView) findViewById(R.id.listViewTransactions);
		chooseCatDisp = (TextView) findViewById(R.id.chooseCatDisp);
		addStoreToDBBtn = (Button) findViewById(R.id.addStoreToDB);
	}

	private void InitializeClickHandlers()
	{
		addStoreToDBBtn.setOnClickListener(this);
	}

	protected void ReadFile() throws IOException 
	{
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("(\\d+/\\d+)\\s+(.*)\\s+(\\d+\\.\\d+)$");
		Matcher m;
		String mon;
		String date;
		String storeName;
		Double amount;
		int index = 0;
		ArrayList<String[]> dateAmtList;
		String[] dateAmtListStr;
		String line;
		while(index < lines.length && index < MAX_COUNT)
		{
			line = lines[index ++];
			m = p.matcher(line);
			if(m.find(0))
			{
				date = m.group(1);
				mon = Utils.AppConstants.GetMonthFromDate(date);
				storeName = m.group(2);
				amount = Double.valueOf(m.group(3));
				if(!StoresDateAmtTable.containsKey(storeName))
				{
					dateAmtList = new ArrayList<String[]>();
					dateAmtListStr = new String[2];
					dateAmtListStr[0] = mon;
					dateAmtListStr[1] = amount.toString();
					dateAmtList.add(dateAmtListStr);
					StoresDateAmtTable.put(storeName, dateAmtList);
					storeNamesList.add(storeName);
				}
				else
				{
					int marker = 0;
					dateAmtListStr = new String[2];
					dateAmtList = StoresDateAmtTable.get(storeName);
					if(mon == dateAmtList.get(0)[0]) //previous month
					{
						marker = 0;
					}
					else if(mon == dateAmtList.get(0)[1])	//current month
					{
						marker = 1;
					}
					dateAmtListStr[0] = mon;
					dateAmtListStr[1] = String.valueOf(Double.valueOf(dateAmtList.get(marker)[1]) + amount);
					dateAmtList.set(marker, dateAmtListStr);						
					StoresDateAmtTable.put(storeName, dateAmtList);
				}
			}			
		}
		GetExistingStoresFromDB(storeNamesList.toArray(new String[storeNamesList.size()]));
	//	GetExistingStoresFromDB(storeNamesList);
		GetNewStores();
		RetrieveStoreCatDB();
	}
	
	///get existing stores from DB and populate new stores array
	protected void GetExistingStoresFromDB(String[] storeNamesArray)
	{
		dbStoresList = storesObj.GetExistingStoresDB(sqlHelper, storeNamesArray);
		for(Stores store : dbStoresList)
		{
			existingStores.add(store.get_name());
			existingCategories.add(store.get_category());
		}
	}
	
	//Get new stores
	private void GetNewStores()
	{
		for(String name : StoresDateAmtTable.keySet())
		{
			if(!existingStores.contains(name))
			{
				newStoresArray.add(name);
			}
			else
			{
				UpdateDBHashMonNAmtStore(existingCategories.get(existingStores.indexOf(name)) , name);
			}
		}
	}

	////Retrieve categories of the stores from the database
	private void RetrieveStoreCatDB()
	{
		if(newStoresArray.size() > 0)
		{
			DisplayNewStores();
		}
		else
		{
			transcationsListView.setVisibility(View.INVISIBLE);
			//	addStoreToDBBtn.setVisibility(View.INVISIBLE);
			chooseCatDisp.setVisibility(View.INVISIBLE);
			//if no new stores then update db
			
		}
	}

	//Get month and amount list for a given store
	private void UpdateDBHashMonNAmtStore(String catName, String storeName)	//TODO Auto-generated method stub 
	{ 
		String prevMonName = "";
		String monName = "";
		String nextMonName = "";
		Double amount1 = 0.0;
		Double amount2 = 0.0;
		ArrayList<String[]> hashDateAmtList;
		ExpenseList2013 expenseListObj = new ExpenseList2013();

		hashDateAmtList = GetHashDateAmt(storeName);	//Get month and amount list for a given store

		for(int i =0; i < hashDateAmtList.size(); i++)
		{
			if(prevMonName == "")
			{
				prevMonName = monName = hashDateAmtList.get(i)[0];
			}
			if(prevMonName == monName)
			{
				amount1 = amount1 + Double.valueOf(hashDateAmtList.get(i)[1]);
			}
			else
			{
				if(nextMonName == "")
				{
					nextMonName = hashDateAmtList.get(i)[0];
				}
				amount2 = amount2 + Double.valueOf(hashDateAmtList.get(i)[1]);
			}
		}
		if(prevMonName != "" && amount1 > 0)
		{
			expenseListObj.UpdateExpenseList(sqlHelper, catName, prevMonName, amount1);
		}
		if(nextMonName != "" && amount2 > 0)
		{
			expenseListObj.UpdateExpenseList(sqlHelper, catName, nextMonName, amount2);
		}
	}

	private ArrayList<String[]> GetHashDateAmt(String storeName) {
		// TODO Auto-generated method stub
		return StoresDateAmtTable.get(storeName);
	}

	private void DisplayNewStores() 
	{
		ArrayList<Transaction> storeList = new ArrayList<Transaction>(); // TODO Auto-generated method stub
		for(int i =0; i < newStoresArray.size(); i++)
		{
			Transaction store = new Transaction();
			store.setStoreName(newStoresArray.get(i));
			store.setCategory(MainActivity.categories);
			storeList.add(store);
		}
		transcationsListView.setVisibility(View.VISIBLE);
		CustomAdapter adapter = new CustomAdapter(this, R.layout.row_grid, storeList);
		transcationsListView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v)	// TODO Auto-generated method stub 
	{
		ArrayList<ContentValues> stores = new ArrayList<ContentValues>();
		ContentValues value;
		String catName = null;
		ViewGroup group;
		if(v == addStoreToDBBtn)
		{
			for(int i =0; i < transcationsListView.getChildCount(); i++)
			{
				value = new ContentValues();
				group = (ViewGroup) transcationsListView.getChildAt(i);
				TextView tView = (TextView) group.getChildAt(0);
				String storeName = tView.getText().toString();
				Spinner spin = (Spinner) group.getChildAt(1);
				catName = spin.getSelectedItem().toString();
				value.put(sqlHelper.STORE_NAME, storeName);	//update category month table
				value.put(sqlHelper.STORE_CATEGORY, catName);
				UpdateDBHashMonNAmtStore(catName, storeName);
				stores.add(value);
			}
			storesObj.UpdateStoreCategories(sqlHelper, stores);
			//			PieGraph pie = new PieGraph();
			//			Double[] values = new Double[5];
			//			String[] categories = {"Grocery Stores", "Restaurants", "Theatres", "Pharmacies", "Others"};
			//			values = GetExpenseValues(categories);
			//			Intent pieIntent = pie.getIntent(this, categories, values);
			//			startActivity(pieIntent);
			Intent in = new Intent(this, DisplayChartsActivity.class);
			startActivity(in);
		}
	}
}


