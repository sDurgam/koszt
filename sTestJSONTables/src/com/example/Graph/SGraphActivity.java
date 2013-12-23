package com.example.Graph;
import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

import com.example.s_expensemanager.*;
import com.example.s_expensemanager.R;

public class SGraphActivity extends Activity
{
	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;	
	public SGraphActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sgraph);
		thread = new Thread()
		{
			public void run()
			{
				for(int i = 0; i < 15; i++)
				{
					try
					{
						Thread.sleep(2000);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					Point p = MockData.getDataFromReceiver(i);	
					line.addNewPoints(p);
					view.repaint();
				}
			}
		};
		thread.start();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		view = line.getView(this);
		setContentView(view);
	}
}
