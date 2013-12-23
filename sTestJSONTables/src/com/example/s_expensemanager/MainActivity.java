package com.example.s_expensemanager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Utils.AppConstants;
import Utils.GDriveConstants;
import Utils.SQLiteDBHelper;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.example.s_expensemanager.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.BackOff;

public class MainActivity extends Activity implements OnClickListener
{
	/**
	 * 
	 */
	Hashtable<String, String> storesTable;
	SQLiteDBHelper helper;
	Button addStoresBtn;
	ListView listViewTransactions; 
	public static ArrayList<String> categories;
	ListView gdFilesView;
	ArrayList<String> lines;

	GoogleAccountCredential credential;
	GDriveAJAXCalls gDriveAjaxObj = null;
	HashMap<String, String> txtFilesMap;
	AsyncTask<String, Integer, String> getAsyncTokenCall;
	ProgressDialog dialog;
	//end driver object

	///To add categories to the Categories table
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		credential = GoogleAccountCredential.usingOAuth2(this.getApplicationContext(), Arrays.asList(AppConstants.driveScope));
		setContentView(R.layout.activity_main);
		startActivityForResult(credential.newChooseAccountIntent(), GDriveConstants.REQUEST_ACCOUNT_PICKER);
		gdFilesView = (ListView) findViewById(R.id.listGDriveFiles);
		gdFilesView.setOnItemClickListener(listener);
		helper = new SQLiteDBHelper(this);
		categories = new ArrayList<String>();
		ClearData();
		GetCategories();
		helper.close();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getAsyncTokenCall = null;
	}


	//G Drive Functions
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			ReadFile(position);
		}
	};

	public void StartIntent(ArrayList<String> lines) {
		// TODO Auto-generated method stub
		Intent transactionsIntent = new Intent(this, ProcessTransactionsFileActivity.class);
		String[] linesArr = lines.toArray(new String[lines.size()]);
		transactionsIntent.putExtra("stores", linesArr);
		startActivity(transactionsIntent);
	}

	protected class GetAsyncTokenTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String ... params) {
			// TODO Auto-generated method stub
						String token = null;
						
							try {
								token = credential.getToken();
								//GoogleAuthUtil.getToken(MainActivity.this.getApplicationContext(), credential.getSelectedAccountName(), AppConstants.driveScope);
							} 
							catch (UserRecoverableAuthException e) 
							{
								//handler.sendEmptyMessage(-2);
								startActivityForResult(e.getIntent(), AppConstants.REQUEST_AUTHORIZATION);
							}
							catch (GoogleAuthException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								handler.sendEmptyMessage(-1);
							}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
			return token;
		}
		protected void onPostExecute(String token)
		{
			if(gDriveAjaxObj == null && token != null)
			{
				gDriveAjaxObj = new GDriveAJAXCalls(MainActivity.this.getApplicationContext(), new AQuery(MainActivity.this), handler, token);
			}
		}
	}



	protected void ReadFile(int position)
	{
		// TODO Auto-generated method stub
		final String currentFileName = (String) gdFilesView.getItemAtPosition(position);
		String dwnLoadURL = txtFilesMap.get(currentFileName);
		gDriveAjaxObj.GetFileContent(dwnLoadURL);
	}

	private final Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			if(msg.what == -1 || msg.what == -2)
			{
				getAsyncTokenCall.cancel(true);
				startActivityForResult(getIntent(), AppConstants.REQUEST_AUTHORIZATION);
			}
			if(msg.what == 10) //back from files list for a folder
			{
				txtFilesMap = new HashMap<String, String>();
				txtFilesMap = (HashMap<String, String>) gDriveAjaxObj.getTextFilesMap();
				PopulateGDFilesListView();
				//destroy the async thread
			}
			if(msg.what == 2)	//back from getting file content
			{
				StartIntent(gDriveAjaxObj.getLines());
			}
		}
	};


	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) 
	{
		switch (requestCode)
		{
		case GDriveConstants.REQUEST_ACCOUNT_PICKER:

			if (resultCode == RESULT_OK && data != null && data.getExtras() != null)
			{
				String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				if (accountName != null) 
				{
					//String webId = "314686612755-3c4j2elqp0douk541od1p7j9b23hmhph.apps.googleusercontent.com";
					//credential.usingAudience(this.getApplicationContext(), "server:client_id:" + webId);
					credential.setSelectedAccountName(accountName);
					if(gDriveAjaxObj == null)
					{
						getAsyncTokenCall = new GetAsyncTokenTask().execute("sai");
					}
				}
			}
			break;
		case GDriveConstants.REQUEST_AUTHORIZATION:
			// See "Handle the authorization intent" section for sample code
			if (resultCode == Activity.RESULT_OK) 
			{
				// App is authorized, you can go back to sending the API request
				if(gDriveAjaxObj == null)
				{
					getAsyncTokenCall = new GetAsyncTokenTask().execute("sai");
				}
			}
			else 
			{
				// User denied access, show him the account chooser again
				startActivityForResult(credential.newChooseAccountIntent(), GDriveConstants.REQUEST_ACCOUNT_PICKER);
			}
		}
	}

	protected void PopulateGDFilesListView()
	{
		showToast("populate list view");
		String[] gdFilesArray = new String[txtFilesMap.size()];
		int index = 0;
		for(String fileName : txtFilesMap.keySet())
		{
			gdFilesArray[index ++] = fileName;
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_list_item_1, gdFilesArray);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		gdFilesView.setAdapter(adapter);
		gdFilesView.setVisibility(View.VISIBLE);
	}

	public void showToast(final String toast) 
	{
		Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
	}



	//End G Drive Functions

	private void ClearData()
	{
		SQLiteDatabase clearDB = helper.getWritableDatabase();
		//clearDB.delete(helper.TABLE_STORES, null, null);
		clearDB.beginTransaction();
		ContentValues cvalue = new ContentValues();
		cvalue.put(helper.EXPENSELIST_JAN, 0.0);
		cvalue.put(helper.EXPENSELIST_FEB, 0.0);
		cvalue.put(helper.EXPENSELIST_MAR, 0.0);
		cvalue.put(helper.EXPENSELIST_APR, 0.0);
		cvalue.put(helper.EXPENSELIST_MAY, 0.0);
		cvalue.put(helper.EXPENSELIST_JUN, 0.0);
		cvalue.put(helper.EXPENSELIST_JUL, 0.0);
		cvalue.put(helper.EXPENSELIST_AUG, 0.0);
		cvalue.put(helper.EXPENSELIST_SEP, 0.0);
		cvalue.put(helper.EXPENSELIST_OCT, 0.0);
		cvalue.put(helper.EXPENSELIST_NOV, 0.0);
		cvalue.put(helper.EXPENSELIST_DEC, 0.0);
		clearDB.update(helper.TABLE_EXPENSELIST2013, cvalue, null, null);
		//clearDB.delete(helper.TABLE_STORES, null, null);
		clearDB.endTransaction();
		clearDB.close();
	//	ClearStoreData();
	}
	
//	private void ClearStoreData()
//	{
//		SQLiteDatabase clearStoreDB = helper.getWritableDatabase();
//		clearStoreDB.delete(helper.TABLE_STORES, null, null);
//		clearStoreDB.close();
//	}



	private void GetCategories() {
		// TODO Auto-generated method stub
		String category;
		ArrayList<ContentValues> cvals = new ArrayList<ContentValues>();
		ContentValues val = new ContentValues();
		SQLiteDatabase readCategoriesCall = helper.getReadableDatabase();
		String[] allColums = {"Name"};
		Cursor categoriesCursor = readCategoriesCall.query(SQLiteDBHelper.TABLE_CATEGORIES, allColums, null, null, null, null, null);
		//if no categroies then read from json and add to DB
		if(categoriesCursor.getCount() == 0)
		{
			ParseJsonAddCategoriesTableData();

		}
		else
		{
			categoriesCursor.moveToFirst();
			do
			{
				category = categoriesCursor.getString(0);
				categories.add(category);
			}while(categoriesCursor.moveToNext());
		}
		readCategoriesCall.close();

	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == addStoresBtn)
		{

			Intent in = new Intent(this, AddStoresActivity.class);
			startActivity(in);
		}
	}

	//	private void ParseJsonAddStoresTableData() {
	//		// TODO Auto-generated method stub
	//		ArrayList<ContentValues> storesList = new ArrayList<ContentValues>();
	//		ContentValues store = new ContentValues();
	//		store.put(SQLiteDBHelper.STORE_NAME, "Whole Foods Market");
	//		store.put(SQLiteDBHelper.STORE_CATEGORY, "Grocery Stores");
	//		storesList.add(store);
	//		store = new ContentValues();
	//		store.put(SQLiteDBHelper.STORE_NAME, "Trader Joe's");
	//		store.put(SQLiteDBHelper.STORE_CATEGORY, "Grocery Stores");
	//		storesList.add(store);
	//		store = new ContentValues();
	//		store.put(SQLiteDBHelper.STORE_NAME, "Bazaar West");
	//		store.put(SQLiteDBHelper.STORE_CATEGORY, "Grocery Stores");
	//		storesList.add(store);
	//		store = new ContentValues();
	//		store.put(SQLiteDBHelper.STORE_NAME, "Stop & Shop");
	//		store.put(SQLiteDBHelper.STORE_CATEGORY, "Grocery Stores");
	//		storesList.add(store);
	//		
	//		
	//		
	//	}

	//	To add different categories to the table
	private void ParseJsonAddCategoriesTableData() {
		// TODO Auto-generated method stub
		StringBuilder jsonBuilder = new StringBuilder();
		String json = "";
		String line = "";
		BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.categoriesjson));
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
		try {
			while((line = reader.readLine()) != null)
			{
				jsonBuilder.append(line); 
			}
			reader.close();
			resourceStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jsonBuilder != null)
		{
			json = jsonBuilder.toString();
		}
		SQLiteDatabase dBase = helper.getWritableDatabase();
		JSONArray jArray = null;
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj != null)
			{
				jArray = jsonObj.getJSONArray("HeadingText");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<ContentValues> categoryNames = new ArrayList<ContentValues>();
		ArrayList<ContentValues> expenseCatNames = new ArrayList<ContentValues>();
		ContentValues cvs;
		ContentValues excvs;
		JSONObject categoryData;
		if(jArray != null)
		{
			try {
				for(int i =0; i < jArray.length(); i ++)
				{
					cvs = new ContentValues();
					categoryData = jArray.getJSONObject(i);  
					cvs.put(SQLiteDBHelper.KEY_NAME, categoryData.getString("Name"));
					categories.add(categoryData.getString("Name"));
					categoryNames.add(cvs);
					excvs = new ContentValues();
					excvs.put(SQLiteDBHelper.EXPENSELIST_CATEGORY, categoryData.getString("Name"));
					excvs.put(SQLiteDBHelper.EXPENSELIST_JAN, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_FEB, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_MAR, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_APR, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_MAY, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_JUN, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_JUL, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_AUG, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_SEP, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_OCT, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_NOV, 0.0);
					excvs.put(SQLiteDBHelper.EXPENSELIST_DEC, 0.0);
					expenseCatNames.add(excvs);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(ContentValues category : categoryNames)
			{
				dBase.insertWithOnConflict(SQLiteDBHelper.TABLE_CATEGORIES, null, category, SQLiteDatabase.CONFLICT_REPLACE);

			}
			for(ContentValues ecvs : expenseCatNames)
			{
				dBase.insertWithOnConflict(SQLiteDBHelper.TABLE_EXPENSELIST2013, null, ecvs, SQLiteDatabase.CONFLICT_REPLACE);
			}
		}
		dBase.close();
		//	helper.close();	
	}







}
