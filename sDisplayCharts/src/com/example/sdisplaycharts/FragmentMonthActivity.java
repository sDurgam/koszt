package com.example.sdisplaycharts;

import java.util.Calendar;

import org.achartengine.GraphicalView;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FragmentMonthActivity extends Fragment implements OnItemSelectedListener 
{
	Spinner spinnerMonths;
	LinearLayout ll;
	int[] colorsArray = new int[] { Color.parseColor("#F2846B"),
	        Color.parseColor("#A01115"), Color.parseColor("#741E1E") };
	String[] labels = {"groceries", "gas sation", "others"};
	double[] graphValues = {10.2, 89.4, 23.56};
	double[][] values = {{10.2,11,12,13,14,15,16,17,18,19,20,21},{22,24,26,28,30,8,9,13,67,78,34,11.5},{78,90,77,73,80,35,30.6,31.2,35.7,89.0,47.3,56.2}};
	int option;
	SingleDonutGraph donutGraph;
	PieChart pieGraph;
	BubbleGraph bubbleGraph;
	GraphicalView chartView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		option = getArguments().getInt("chart");
		View view = inflater.inflate(R.layout.fragment_activity, container, false);
		ll = (LinearLayout) view.findViewById(R.id.ll);
		spinnerMonths = (Spinner) view.findViewById(R.id.spinnerMonths);
		spinnerMonths.setOnItemSelectedListener(this);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		spinnerMonths.setSelection(month - 1);
		donutGraph = new SingleDonutGraph();
		pieGraph = new PieChart();
		bubbleGraph = new BubbleGraph();
		chartView = DisplayChart(getActivity().getApplicationContext(), colorsArray, labels, graphValues, values);
		if(chartView != null)
			ll.addView(chartView);
		return view;
	}
	
	protected GraphicalView DisplayChart(Context ctx, int[] colorsArray, String[] labels, double[] graphValues, double[][] values)
	{
		// TODO Auto-generated method stub
		switch(option)
		{
		case 1:
			chartView = (GraphicalView) donutGraph.execute(this.getActivity().getApplication().getBaseContext(), colorsArray, labels, graphValues);
			break;
		case 2:
			chartView = (GraphicalView) pieGraph.execute(this.getActivity().getApplication().getBaseContext(), colorsArray, labels, graphValues);
			break;
		case 3:
			chartView = (GraphicalView) bubbleGraph.execute(this.getActivity().getApplication().getBaseContext(), colorsArray, labels, values);
			break;
		}
		return chartView;
	}
	@Override
	public void onItemSelected(AdapterView<?> adapter, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		chartView = DisplayChart(getActivity().getApplicationContext(), colorsArray, labels, graphValues, values);
		if(chartView != null)
		{
			ll.removeViewAt(1);
			ll.addView(chartView);
		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
