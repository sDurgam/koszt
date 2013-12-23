package com.example.Graph;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import ORClasses.Categories;
import ORClasses.ExpenseList2013;
import Utils.AppConstants;
import Utils.SQLiteDBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LineChartActivity extends Activity {
	Categories catObj;
	SQLiteDBHelper sqlHelper;
	ArrayList<ExpenseList2013> expenses2013List;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		catObj = new Categories();
		sqlHelper = new SQLiteDBHelper(this);
		String[] categories = catObj.GetExistingCategoriesDB(sqlHelper);
		ArrayList<ExpenseList2013> expenses2013List = catObj.GetCategoryExpenses(sqlHelper, sqlHelper.TABLE_EXPENSELIST2013, AppConstants.cols);
		sqlHelper.close();
		Intent lineIntent = getIntent(categories, expenses2013List);
		startActivity(lineIntent);
	}

	public Intent getIntent(String[] zcategories, ArrayList<ExpenseList2013> expensesList)
	{

		int colorsLength = AppConstants.COLORS.length;
		int pointStylesLength = AppConstants.pointStyles.length;
		int colorIndex = 0;
		int pointStylesIndex = 0;
		boolean isExistingCategory;
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Category Vs Expense Chart");
		multiRenderer.setXTitle("Year 2013");
		multiRenderer.setYTitle("$");
		multiRenderer.setZoomButtonsVisible(true);

		for(int i = 0; i < AppConstants.xmonths.length; i++)
		{
			multiRenderer.addXTextLabel(i + 1, AppConstants.xmonths[i]);
		}
		for(ExpenseList2013 expense : expensesList)
		{
			isExistingCategory = IsCategory(zcategories, expense.getCategory());
			if(isExistingCategory)
			{
				XYSeries series = new XYSeries(expense.getCategory());
				series.add(AppConstants.x_axis[0], expense.getJanAmt());
				series.add(AppConstants.x_axis[1], expense.getFebAmt());
				series.add(AppConstants.x_axis[2], expense.getMarAmt());
				series.add(AppConstants.x_axis[3], expense.getAprAmt());
				series.add(AppConstants.x_axis[4], expense.getMayAmt());
				series.add(AppConstants.x_axis[5], expense.getJunAmt());
				series.add(AppConstants.x_axis[6], expense.getJulAmt());
				series.add(AppConstants.x_axis[7], expense.getAugAmt());
				series.add(AppConstants.x_axis[8], expense.getSepAmt());
				series.add(AppConstants.x_axis[9], expense.getOctAmt());
				series.add(AppConstants.x_axis[10], expense.getNovAmt());
				series.add(AppConstants.x_axis[11], expense.getDecAmt());
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
				renderer.setColor(AppConstants.COLORS[colorIndex++]);
				renderer.setPointStyle(AppConstants.pointStyles[pointStylesIndex++]);
				renderer.setFillPoints(true);
				renderer.setLineWidth(2);
				renderer.setDisplayChartValues(true);
				multiRenderer.addSeriesRenderer(renderer);
			}
		}
		Intent i = ChartFactory.getLineChartIntent(this.getApplicationContext(), dataset, multiRenderer);
		
		//Intent i = ChartFactory.getBarChartIntent(this.getApplicationContext(), dataset, multiRenderer, BarChart.Type.DEFAULT, "Expense Graph");
		
		return i;	
	}

	private boolean IsCategory(String[] zcategories, String category)
	{
		for(int i =0; i < zcategories.length; i++)
		{
			if(zcategories[i].equals(category))
				return true;
		}
		return false;
	}

}
