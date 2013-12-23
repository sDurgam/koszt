package com.example.Graph;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class LineGraph {
	private GraphicalView view;
	private TimeSeries dataset = new TimeSeries("Rain Fall");
	private XYMultipleSeriesDataset mDataSet = new XYMultipleSeriesDataset();
	private XYSeriesRenderer renderer = new XYSeriesRenderer();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

	public LineGraph() {
		// TODO Auto-generated constructor stub
		mDataSet.addSeries(dataset);
		
		renderer.setColor(Color.BLACK);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setXTitle("Day 1 #");
		mRenderer.setYTitle("CM RainFall");
		mRenderer.addSeriesRenderer(renderer);	
	}
	
	public GraphicalView getView(Context context)
	{
		view = ChartFactory.getLineChartView(context, mDataSet, mRenderer);
		return view;
	}
	
	public void addNewPoints(Point p)
	{
		dataset.add(p.getX(), p.getY());
	}
}
