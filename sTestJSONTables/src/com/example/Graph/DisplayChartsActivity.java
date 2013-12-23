package com.example.Graph;

import com.example.s_expensemanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DisplayChartsActivity extends Activity implements OnClickListener {

	Button lineGraphBtn;
	Button pieGraphBtn;
	Button bubbleGraphBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaycharts);
		lineGraphBtn = (Button) findViewById(R.id.lineGraphBtn);
		pieGraphBtn = (Button) findViewById(R.id.pieGraphBtn);
		bubbleGraphBtn = (Button) findViewById(R.id.bubbleGraphBtn);
		lineGraphBtn.setOnClickListener(this);
		pieGraphBtn.setOnClickListener(this);
		bubbleGraphBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == lineGraphBtn)
		{
			Intent in = new Intent(this, LineChartActivity.class);
			startActivity(in);
		}
	}
}
