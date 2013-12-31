package Utils;

import org.achartengine.chart.PointStyle;

import android.graphics.Color;

public class AppConstants {

	public static final String jan_month = "jan";
	public static final String feb_month = "feb";
	public static final String mar_month = "mar";
	public static final String apr_month = "apr";
	public static final String may_month = "may";
	public static final String jun_month = "jun";
	public static final String jul_month = "jul";
	public static final String aug_month = "aug";
	public static final String sep_month = "sep";
	public static final String oct_month = "oct";
	public static final String nov_month = "nov";
	public static final String dec_month = "dec";

	public AppConstants() {
		// TODO Auto-generated constructor stub
	}
	public static String GetMonthFromDate(String dateStr) 
	{
		Integer mon = Integer.valueOf(dateStr.split("/")[0]);		// TODO Auto-generated method stub
		if(mon == 1)
		{
			return Utils.AppConstants.jan_month;
		}
		else if(mon == 2)
		{
			return Utils.AppConstants.feb_month;
		}
		if(mon == 3)
		{
			return Utils.AppConstants.mar_month;
		}
		else if(mon == 4)
		{
			return Utils.AppConstants.apr_month;
		}
		if(mon == 5)
		{
			return Utils.AppConstants.may_month;
		}
		else if(mon == 6)
		{
			return Utils.AppConstants.jun_month;
		}
		if(mon == 7)
		{
			return Utils.AppConstants.jul_month;
		}
		else if(mon == 8)
		{
			return Utils.AppConstants.aug_month;
		}
		if(mon == 9)
		{
			return Utils.AppConstants.sep_month;
		}
		else if(mon == 10)
		{
			return Utils.AppConstants.oct_month;
		}
		if(mon == 11)
		{
			return Utils.AppConstants.nov_month;
		}
		else if(mon == 12)
		{
			return Utils.AppConstants.dec_month;
		}
		return null;
	}
	
	public static String GetMonthFromInteger(int monNum) 
	{
		
		if(monNum == 1)
		{
			return Utils.AppConstants.jan_month;
		}
		else if(monNum == 2)
		{
			return Utils.AppConstants.feb_month;
		}
		if(monNum == 3)
		{
			return Utils.AppConstants.mar_month;
		}
		else if(monNum == 4)
		{
			return Utils.AppConstants.apr_month;
		}
		if(monNum == 5)
		{
			return Utils.AppConstants.may_month;
		}
		else if(monNum == 6)
		{
			return Utils.AppConstants.jun_month;
		}
		if(monNum == 7)
		{
			return Utils.AppConstants.jul_month;
		}
		else if(monNum == 8)
		{
			return Utils.AppConstants.aug_month;
		}
		if(monNum == 9)
		{
			return Utils.AppConstants.sep_month;
		}
		else if(monNum == 10)
		{
			return Utils.AppConstants.oct_month;
		}
		if(monNum == 11)
		{
			return Utils.AppConstants.nov_month;
		}
		else if(monNum == 12)
		{
			return Utils.AppConstants.dec_month;
		}
		return null;
	}
	
	
	
	/* Google Drive Constants */
	public static final String driveScope = "https://www.googleapis.com/auth/drive";
	public static final String GetFolderIdString = "https://www.googleapis.com/drive/v2/files?q=title%3D's_expensemanager'&mimeType%3D'application%2Fvnd.google-apps.folder'&access_token=";
	public static final String GetFileListString = "https://www.googleapis.com/drive/v2/files?q='" + "%s" +  "'+in+parents" + "+and+mimeType%3D'text%2Fplain'+and+trashed+%3D+false&fields=items(downloadUrl%2Cid%2Ctitle)&" + "access_token=";
	
	public static final String GetFolderIdCallback = "GetFolderIdCallback"; 
	public static final String GetFileListCallback = "GetFileListCallback";
	public static final String GetFileContent = "GetFileContent";
	public static final String GetFileContentCallback = "GetFileContentCallback";
	
	public static final String GetFolderIdFailure = "Failed to get folder from google drive";
	public static final String GetFileListFailure = "Failed to list files in the s_expensemanager folder";
	/* End Google Drive Constants */
	/*chart constants */
	public static final String[] xmonths = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
	public static final String[] cols = {SQLiteDBHelper.EXPENSELIST_CATEGORY, SQLiteDBHelper.EXPENSELIST_JAN, SQLiteDBHelper.EXPENSELIST_FEB, SQLiteDBHelper.EXPENSELIST_MAR, SQLiteDBHelper.EXPENSELIST_APR, SQLiteDBHelper.EXPENSELIST_MAY, SQLiteDBHelper.EXPENSELIST_JUN, SQLiteDBHelper.EXPENSELIST_JUL, SQLiteDBHelper.EXPENSELIST_AUG, SQLiteDBHelper.EXPENSELIST_SEP, SQLiteDBHelper.EXPENSELIST_OCT, SQLiteDBHelper.EXPENSELIST_NOV, SQLiteDBHelper.EXPENSELIST_DEC};
	public static final int x_axis[] = {1,2,3,4,5,6,7,8,9,10,11,12};
	public static final int[] COLORSARR = {Color.parseColor("#F2846B"),
        Color.parseColor("#A01115"), Color.parseColor("#741E1E"), Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.MAGENTA, Color.TRANSPARENT};
	public static final PointStyle[] pointStylesArray = {PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.POINT, PointStyle.SQUARE, PointStyle.TRIANGLE};
	/*end chart constants */
	public static final int REQUEST_ACCOUNT_PICKER = 1;
	public static final int REQUEST_AUTHORIZATION = 2;
}
