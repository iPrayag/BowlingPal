/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.adeptima.bowling.charts;

import java.util.ArrayList;
import java.util.List;

import jp.co.adeptima.bowling.DBAdapter;


import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Sales demo bar chart.
 */
public class SalesStackedBarChart extends ChartRenderers {
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Sales stacked bar chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The monthly sales for the last 2 years (stacked bar chart)";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		DBAdapter db = new DBAdapter(context);
		String[] titles = new String[] { "" };
		List<double[]> values = new ArrayList<double[]>();
		// TODO I have to find better way to know the numbers of rows returned by querry.
		int z = 0;
		Cursor d = db.getDailyAverage();
		if (d.moveToFirst()) {
			do {
			z++;
			} while (d.moveToNext());
		}
		db.close();
		
		int i = 0;
		
		Cursor c = db.getDailyAverage();
		values.add(new double[z]);
		if (c.moveToFirst()) {
			do {
				
				try {
					
					values.get(0)[i] = Double.valueOf(c.getString(1));
					i++;
				} catch (NumberFormatException  e) {
					// TODO Auto-generated catch block
				}
			} while (c.moveToNext());
		}
		db.close();
		
		
		int[] colors = new int[] { Color.CYAN };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		setChartSettings(renderer, "Average daily scores",
				"Each bar represents the day you played bowling", "Score", 0.5, 12.5, 0, 300, Color.GRAY,
				Color.LTGRAY);
		renderer.setXLabels(2);
		renderer.setYLabels(2);

		renderer.setDisplayChartValues(true);
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		// renderer.setPanEnabled(false);
		// renderer.setZoomEnabled(false);
		renderer.setZoomRate(1.1f);
		renderer.setBarSpacing(0.5);
		return ChartFactory.getBarChartIntent(context,
				buildBarDataset(titles, values), renderer, Type.STACKED);
	}

}
