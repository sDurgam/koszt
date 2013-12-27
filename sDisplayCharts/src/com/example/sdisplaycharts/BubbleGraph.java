package com.example.sdisplaycharts;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.LinearLayout;

public class BubbleGraph {

	GraphicalView view;
	public GraphicalView execute(Context context, int[] colorsArray , String[] labels,  double[][] values ) 
	{
		int count = values.length;
		int[] colors = new int[count];
		for (int i = 0; i < count; i++) 
		{
			colors[i] = colorsArray[i];
		}
		final String[] xmonths = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
		final int x_axis[] = {1,2,3,4,5,6,7,8,9,10,11,12};
		final int y_axis[] = {1,2,3};

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer renderer;

		//multiRenderer.setAxisTitleTextSize(16);
		multiRenderer.setChartTitleTextSize(20);
		//	multiRenderer.setLabelsTextSize(15);
		//	multiRenderer.setLegendTextSize(15);
		multiRenderer.setZoomButtonsVisible(true);
		//	multiRenderer.setShowGrid(true);
		//multiRenderer.setShowAxes(true);


		double currentY;

		for(int i = 0; i < xmonths.length; i++)
		{
			multiRenderer.addXTextLabel(i + 1, xmonths[i]);
		}
		for(int i = 0; i < labels.length; i++)
		{
			multiRenderer.addYTextLabel(i + 1, labels[i]);
		}
		
		for(int k=0; k < y_axis.length; k++) 
			{
				XYValueSeries series = new XYValueSeries(labels[k]);
				for(int j =0; j < x_axis.length; j++)
				{
					currentY = values[k][j];
					series.add(x_axis[j], y_axis[k], currentY);
				}
				dataset.addSeries(series);
				renderer = new XYSeriesRenderer();
				renderer.setColor(colors[k]);
				multiRenderer.addSeriesRenderer(renderer);
			}
	//	System.out.println(series.getItemCount());
		
		//renderer.setPointStyle(pointStyles[i]);
		//renderer.setFillPoints(true);
		//renderer.setLineWidth(2);
		//renderer.setDisplayChartValues(true);
		//renderer.setGradientEnabled(true);
		//renderer.setStroke(BasicStroke.DOTTED);
		
		view = ChartFactory.getBubbleChartView(context, dataset, multiRenderer);
//		return ChartFactory.getBubbleChartIntent(context, dataset,
//				multiRenderer, null);
		return view;
	}

}
