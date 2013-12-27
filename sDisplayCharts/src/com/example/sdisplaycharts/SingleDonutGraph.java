package com.example.sdisplaycharts;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.LinearLayout;

public class SingleDonutGraph {
	


	public GraphicalView execute(Context context, int[] colorsArray , String[] labels,  double values[]) 
	{
	    int count = values.length;
	    int[] colors = new int[count];
	    for (int i = 0; i < count; i++) {
	        colors[i] = colorsArray[i];
	    }
	    DefaultRenderer renderer = buildCategoryRenderer(colors);
	    renderer.setShowLabels(true);
	    renderer.setLabelsColor(Color.BLACK);
	    renderer.setBackgroundColor(Color.WHITE);
	    renderer.setPanEnabled(false);// Disable User Interaction
	    renderer.setScale((float) 1.4);
	    renderer.setInScroll(true); //To avoid scroll Shrink        
	    renderer.setStartAngle(90);
	    renderer.setShowLegend(false);
	    MultipleCategorySeries categorySeries = new MultipleCategorySeries(
	            "Punch Graph");
	    categorySeries.add(labels, values);

	    return ChartFactory.getDoughnutChartView(context, categorySeries,
	            renderer);
	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	    DefaultRenderer renderer = new DefaultRenderer();
	    for (int color : colors) {
	        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	        r.setColor(color);
	        renderer.addSeriesRenderer(r);

	    }
	    return renderer;
	}

}
