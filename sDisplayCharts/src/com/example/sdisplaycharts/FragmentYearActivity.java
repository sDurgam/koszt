package com.example.sdisplaycharts;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentYearActivity extends Fragment  {

	int[] colorsArray = new int[] { Color.parseColor("#F2846B"),
	        Color.parseColor("#A01115"), Color.parseColor("#741E1E") };
	public static final String[] xmonths = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
	public static final PointStyle[] pointStyles = {PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.POINT, PointStyle.SQUARE, PointStyle.TRIANGLE};
	String[] labels = {"groceries", "gas sation", "others"};
	double[] graphValues = {10.2, 89.4, 23.56};
	double[][] values = {{10.2,11,12,13,14,15,16,17,18,19,20,21},{22,24,26,28,30,8,9,13,67,78,34,11.5},{78,90,77,73,80,35,30.6,31.2,35.7,89.0,47.3,56.2}};
	int option;
	LinearLayout llyear;
	GraphicalView chartView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		option = getArguments().getInt("chart");
		View view = inflater.inflate(R.layout.fragment_yearactivity, container, false);
		llyear = (LinearLayout) view.findViewById(R.id.llyear);
		
		chartView = DisplayChart(getActivity().getApplicationContext(), colorsArray, labels, graphValues, values);
		if(chartView != null)
			llyear.addView(chartView);
		return view;
	}
	
	protected GraphicalView DisplayChart(Context ctx, int[] colorsArray, String[] labels, double[] graphValues, double[][] values)
	{
		// TODO Auto-generated method stub
		switch(option)
		{
		case 3:
		case 4:
		case 5:
		case 6:
			chartView = (GraphicalView) new AnnualGraph().execute(this.getActivity().getApplication().getBaseContext(), labels, colorsArray, pointStyles, xmonths, values, option);
			break;
		}
		return chartView;
	}

}
