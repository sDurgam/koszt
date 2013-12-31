package Utils;

import java.util.ArrayList;

import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class Helper {

	public static String makePlaceholders(int size) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(size * 2 - 1);
		sb.append("?");
		for(int i = 1; i < size; i++)
		{
			sb.append(",?");
		}
		return sb.toString();
	}
	
	public static String makePlaceholders(int size, ArrayList<String> str) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(size * 2 - 1);
		String sMarker = "'";
		String eMarker = ",'";
		sb.append(sMarker + str.get(0) + eMarker);
		for(int i = 1; i < size; i++)
		{
			sb.append(eMarker + str.get(i) + sMarker);
		}
		return sb.toString();
	}
	
	public static int ResetColorIndex()
	{
		return 0;
	}
	public static int ResetPointStylesIndex()
	{
		return 0;
	}
	
	public static boolean IsCategory(String[] zcategories, String category)
	{
		for(int i =0; i < zcategories.length; i++)
		{
			if(zcategories[i].equals(category))
				return true;
		}
		return false;
	}

	public static DefaultRenderer buildCategoryRenderer(int size) {
	    DefaultRenderer renderer = new DefaultRenderer();
	    int colorIndex = 0;
	    int colorsArrayLength = AppConstants.COLORSARR.length;
	    
	    for (int index = 0; index < size; index++)
	    {
	        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	        r.setColor(AppConstants.COLORSARR[colorIndex]);
	        if(colorIndex == colorsArrayLength - 1)
	        {
	        	colorIndex = Helper.ResetColorIndex();
	        }
	        colorIndex++;
	        renderer.addSeriesRenderer(r);
	    }
	    return renderer;
	}
}
