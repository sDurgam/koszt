package com.example.Graph;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieGraph {

	public Intent getIntent(Context ctx, String[] categories, Double[] values)
	{
		
		CategorySeries series = new CategorySeries("Pie Graph");
		for(int i =0; i < categories.length; i++)
		{
			series.add(categories[i], values[i]);
		}
		DefaultRenderer renderer = new DefaultRenderer();
		int[] colors = { Color.BLUE, Color.DKGRAY, Color.GREEN, Color.YELLOW, Color.MAGENTA};
		for(int color : colors)
		{			
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		Intent intent = ChartFactory.getPieChartIntent(ctx, series, renderer, "Expense chart");
		return intent;

	}
}

