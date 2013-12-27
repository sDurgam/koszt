//package com.example.sdisplaycharts;
//
//import org.achartengine.ChartFactory;
//import org.achartengine.GraphicalView;
//import org.achartengine.model.CategorySeries;
//import org.achartengine.model.MultipleCategorySeries;
//import org.achartengine.renderer.DefaultRenderer;
//import org.achartengine.renderer.SimpleSeriesRenderer;
//import org.achartengine.renderer.XYMultipleSeriesRenderer;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.widget.LinearLayout;
//
//public class CubicChart {
//
//	int[] Mycolors = new int[] {Color.GREEN, Color.BLUE, Color.YELLOW};
//	String[] labels = {"groceries", "gas sation", "others"};
//
//
//	public Intent execute(Context context, LinearLayout parent,double values[]) {
//	    parent.removeAllViews();
//	    int[] colors = new int[count];
//	    for (int i = 0; i < count; i++) {
//	        colors[i] = Mycolors[i];
//	    }
//	    DefaultRenderer renderer = buildCategoryRenderer(colors);
//	    renderer.setShowLabels(true);
//	    renderer.setLabelsColor(Color.BLACK);
//	    renderer.setBackgroundColor(Color.WHITE);
//	    renderer.setPanEnabled(false);// Disable User Interaction
//	    renderer.setScale((float) 1.4);
//	    renderer.setInScroll(true); //To avoid scroll Shrink        
//	    renderer.setStartAngle(90);
//	    renderer.setShowLegend(false);
//
//	   
//
//	    MultipleCategorySeries categorySeries = new MultipleCategorySeries(
//	            "Punch Graph");
//	    categorySeries.add(labels, values);
//	    XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
//	    multiRenderer.addSeriesRenderer(renderer);
//	    CategorySeries s = new CategorySeries("expenses");
//	    for(int i =0; i < count; i++)
//	    {
//	    	s.add(labels[i], values[i]);
//	    }
//
//	    mChartView2 = ChartFactory.getCubeLineChartView(context,
//	            s, renderer, 0.4);
//
//	    parent.addView(mChartView2);
//
//	    return ChartFactory.getPieChartIntent(context, s,
//	            renderer, "Expenses");
//	}
//
//	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
//	    DefaultRenderer renderer = new DefaultRenderer();
//	    for (int color : colors) {
//	        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//	        r.setColor(color);
//	        renderer.addSeriesRenderer(r);
//
//	    }
//	    return renderer;
//	}
//
//}
