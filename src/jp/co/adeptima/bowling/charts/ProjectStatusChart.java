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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.adeptima.bowling.DBAdapter;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

/**
 * Project status demo chart.
 */
public class ProjectStatusChart extends ChartRenderers {

	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Project tickets status";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The opened tickets and the fixed tickets (time chart)";
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
		List<Date[]> dates = new ArrayList<Date[]>();
		List<double[]> values = new ArrayList<double[]>();
		
		int z = 0;
		Cursor d = db.getMonthlyAverage();
		if (d.moveToFirst()) {
			do {
			z++;
			} while (d.moveToNext());
		}
		db.close();
		
		dates.add(new Date[z]);
		values.add(new double[z]);
		int i = 0;
	    DateFormat dateformat = new SimpleDateFormat("yyyy-MM");
		
		Cursor c = db.getMonthlyAverage();
		if (c.moveToFirst()) {
			do {
				try {

					Date today = dateformat.parse(c.getString(0));
					dates.get(0)[i] = today;
					values.get(0)[i] = Double.valueOf(c.getString(1));
					i++;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
				}
				// DisplayTitle(c4);
			} while (c.moveToNext());
		}
		db.close();

		String[] titles = new String[] { "New tickets" };

	
		int[] colors = new int[] { Color.GREEN };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer, "All Time Scores", "Date", "Score",
				dates.get(0)[0].getTime(), dates.get(0)[z-1].getTime(), 100, 300,
				Color.GRAY, Color.LTGRAY);
		renderer.setXLabels(5);
		renderer.setYLabels(10);
		renderer.setDisplayChartValues(true);
		return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, dates, values),
				renderer,"yyyy-MM");
	}

	

}
