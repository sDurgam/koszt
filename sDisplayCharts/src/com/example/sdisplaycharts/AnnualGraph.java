package com.example.sdisplaycharts;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;



public class AnnualGraph {
	int[] x_axis = {1,2,3,4,5,6,7,8,9,10,11,12};
	
	public GraphicalView execute(Context context, String[] labels, int[] colorsArray, PointStyle[] pointStylesArray, String[] xmonths, double[][] values, int option)
	{

		int colorsLength = colorsArray.length;
		int pointStylesLength = pointStylesArray.length;
		int colorIndex = 0;
		int pointStylesIndex = 0;
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Category Vs Expense Chart");
		multiRenderer.setXTitle("Year 2013");
		multiRenderer.setYTitle("$");
		multiRenderer.setZoomButtonsVisible(true);

		for(int i = 0; i < xmonths.length; i++)
		{
			multiRenderer.addXTextLabel(i + 1, xmonths[i]);
		}
		for(int i =0; i < labels.length; i++)
		{
			XYSeries series = new XYSeries(labels[i]);
			for(int j =0; j < xmonths.length; j++)
			{
				series.add(x_axis[j], values[i][j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer renderer = new XYSeriesRenderer();
				if(colorIndex == colorsLength - 1)
				{
					colorIndex = 0;
				}
				if(pointStylesIndex == pointStylesLength - 1)
				{
					pointStylesIndex = 0;
				}
				renderer.setColor(colorsArray[colorIndex++]);
				renderer.setPointStyle(pointStylesArray[pointStylesIndex++]);
				renderer.setFillPoints(true);
				renderer.setLineWidth(2);
				renderer.setDisplayChartValues(true);
				multiRenderer.addSeriesRenderer(renderer);
		}
		if(option == 3)
			return ChartFactory.getBubbleChartView(context, dataset, multiRenderer);
		if(option == 4)
			return ChartFactory.getLineChartView(context, dataset, multiRenderer);
		if(option == 5)
			return ChartFactory.getBarChartView(context, dataset, multiRenderer, org.achartengine.chart.BarChart.Type.STACKED);
		else if(option == 6)
		{
			float smoothness = 0.5f;
			return ChartFactory.getCubeLineChartView(context, dataset, multiRenderer, smoothness);
		}
		return null;
	}
}
