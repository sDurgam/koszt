package com.example.s_expensemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Utils.AppConstants;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


public class GDriveAJAXCalls extends Activity
{
	AQuery aq;
	//GoogleAccountCredential credential;
	Context ctx;
	private String token;
	HashMap<String, String> textFilesMap;
	Handler handler;
	ArrayList<String> lines;
	

	public GDriveAJAXCalls(Context ctx, AQuery aq, Handler handler, String token)
	{
		this.ctx = ctx;
		this.aq = aq;
		this.token = token;
		this.handler = handler;
		asyncGetFolderId();
	}


	public ArrayList<String> getLines() {
		return lines;
	}

	protected void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}

	public HashMap<String, String> getTextFilesMap() {
		return textFilesMap;
	}

	protected void setTextFilesMap(HashMap<String, String> textFilesMap) {
		this.textFilesMap = textFilesMap;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void asyncGetFolderId()
	{
		//kill the async thread
		//getAsyncToken.cancel(true);
		String url = AppConstants.GetFolderIdString + token;
		aq.ajax(url, JSONObject.class, this, AppConstants.GetFolderIdCallback);
	}
	public void asyncGetFilesList(String folderId)
	{
		String url;
		//= String.format(AppConstants.GetFileListString, folderId, token);
		url = "https://www.googleapis.com/drive/v2/files?q='" + folderId +  "'+in+parents" + "+and+mimeType%3D'text%2Fplain'+and+trashed+%3D+false&fields=items(downloadUrl%2Cid%2Ctitle)&" + "access_token=" + token;
		try
		{
			aq.ajax(url, JSONObject.class, this, AppConstants.GetFileListCallback);
		}
		catch(Exception e)
		{
			url = null;
		}
	}

	/* CALLBACK REGIONS */
	public void GetFolderIdCallback(String url, JSONObject json, AjaxStatus status){
		if(json != null){ 
			try
			{
				JSONArray arr = json.getJSONArray("items");
				String folderId = arr.getJSONObject(0).getString("id");
				asyncGetFilesList(folderId);
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		else{          
			Toast.makeText(ctx, AppConstants.GetFolderIdFailure, Toast.LENGTH_LONG).show();
		}
	}


	public void GetFileListCallback(String url, JSONObject json, AjaxStatus status)
	{
		if(json != null)
		{
			try 
			{
				textFilesMap = new HashMap<String, String>();
				JSONArray arr = json.getJSONArray("items");
				JSONObject obj;
				String title;
				String downLoadURL;
				for(int i =0; i < arr.length(); i++)
				{
					obj =  arr.getJSONObject(i);
					title = obj.getString("title");
					downLoadURL = obj.getString("downloadUrl");
					textFilesMap.put(title, downLoadURL);
				}
			} 
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			textFilesMap = null;
			Toast.makeText(ctx, AppConstants.GetFileListFailure, Toast.LENGTH_LONG).show();
		}
		handler.sendEmptyMessage(10);
	}

	public void GetFileContentCallback(String url, File file, AjaxStatus status) throws IOException
	{
		BufferedReader reader = null;
		lines = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		finally
		{
			if(reader != null)
				reader.close();
		}
		handler.sendEmptyMessage(2);
	}

	public void GetFileContent(String downLoadURL) {
		// TODO Auto-generated method stub
		String url = downLoadURL + "&access_token=" + token;
		aq.ajax(url, File.class, this, AppConstants.GetFileContentCallback);
	}


	/* END CALLBACK REGIONS */
	
}
