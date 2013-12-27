package com.example.sdisplaycharts;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnClickListener 
{
	Spinner monthsSpinner;
	Button bubbleBtn;
	Button lineBtn;
	Button barBtn;
	Button donutBtn;
	Button pieBtn;
	int[] colorsArray = new int[] { Color.parseColor("#F2846B"),
	        Color.parseColor("#A01115"), Color.parseColor("#741E1E") };
	//int[] Mycolors = new int[] {Color.GREEN, Color.BLUE, Color.YELLOW};
	String[] labels = {"groceries", "gas sation", "others"};
	double[] graphValues = {10.2, 89.4, 23.56};
	double[][] values = {{10.2,11,12,13,14,15,16,17,18,19,20,21},{22,24,26,28,30,8,9,13,67,78,34,11.5},{78,90,77,73,80,35,30.6,31.2,35.7,89.0,47.3,56.2}};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bubbleBtn = (Button) findViewById(R.id.bubbleBtn);
		donutBtn = (Button) findViewById(R.id.donutBtn);
		pieBtn = (Button) findViewById(R.id.pieBtn);
		bubbleBtn.setOnClickListener(this);
		donutBtn.setOnClickListener(this);
		pieBtn.setOnClickListener(this);
	}



	@Override
	public void onClick(View view) 
	{
		// TODO Auto-generated method stub
//		Intent achartLeft;
//		if(view == donutBtn)
//		{
//			
//			achartLeft = new SingleDonutGraph().execute(this.getApplicationContext(), colorsArray, labels, graphValues);
//			achartLeft.setAction(android.content.Intent.ACTION_VIEW);  
//
//			startActivity(achartLeft);
//		}
//		else if(view == pieBtn)
//		{
//			achartLeft = new PieChart().execute(this.getApplicationContext(), colorsArray, labels, graphValues);
//			startActivity(achartLeft);
//		}
//		else if(view == bubbleBtn)
//		{
////			Intent achartIntentLeft = new BubbleGraph().execute(MainActivity.this,colorsArray, labels, values);
////			startActivity(achartIntentLeft);
//		}
	}
}
