package com.example.sdisplaycharts;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DisplayCharts extends Activity implements OnClickListener {
	Button donutBtn;
	Button bubbleBtn;
	Button pieBtn;
	Button lineBtn;
	Button barBtn;
	Button cubicBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_charts);
		bubbleBtn = (Button) findViewById(R.id.bubbleBtn);
		donutBtn = (Button) findViewById(R.id.donutBtn);
		pieBtn = (Button) findViewById(R.id.pieBtn);
		lineBtn = (Button) findViewById(R.id.lineBtn);
		barBtn = (Button) findViewById(R.id.barBtn);
		cubicBtn = (Button) findViewById(R.id.cubicBtn);
		bubbleBtn.setOnClickListener(this);
		donutBtn.setOnClickListener(this);
		pieBtn.setOnClickListener(this);
		lineBtn.setOnClickListener(this);
		barBtn.setOnClickListener(this);
		cubicBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		Fragment fragment = null;
		FragmentManager manager = getFragmentManager();
		FragmentTransaction ft;
		Bundle args = new Bundle();
		ft = manager.beginTransaction();
		int option = 0;
		// TODO Auto-generated method stub
		if(view == donutBtn)
		{
			option = 1;
		}
		else if(view == pieBtn)
		{
			option = 2;
		}
		else if(view == bubbleBtn)
		{
			option = 3;
		}
		else if(view == lineBtn)
		{
			option = 4;
		}
		else if(view == barBtn)
		{
			option = 5;
		}
		else if(view == cubicBtn)
		{
			option = 6;
		}
		if(option == 1 || option == 2 || option == 3)
		{
			fragment = new FragmentMonthActivity();
		}
		else if(option > 0)
		{
			fragment = new FragmentYearActivity();
		}
		args.putInt("chart", option);
		fragment.setArguments(args);
		ft.replace(R.id.displayFragment, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}
	
}
