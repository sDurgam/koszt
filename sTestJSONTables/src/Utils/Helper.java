package Utils;

import java.util.ArrayList;

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

}
