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
import java.util.List;

import jp.co.adeptima.bowling.DBAdapter;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;


public class SSMonthlyAverageLinear extends ChartRenderers {
	double percOfStrikes;
	double percOfSpares;
  public Intent execute(Context context) {
	int i = 0;
	
	
	DBAdapter db = new DBAdapter(context);
	
	DateFormat dateformat = new SimpleDateFormat("yyyy-MM");
    String[] titles = new String[] { "Strikes", "Spares" };
    List<double[]> dates = new ArrayList<double[]>();
    List<double[]> values = new ArrayList<double[]>();
    
    
    int[] colors = new int[] { Color.BLUE, Color.GREEN,};
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND};
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    
	int z = 0;
	Cursor d = db.ssMonthlyAvgPerc();
	if (d.moveToFirst()) {
		do {
		z++;
		} while (d.moveToNext());
	}
	db.close();
    
	dates.add(new double[z]);
	dates.add(new double[z]);
	values.add(new double[z]);
	values.add(new double[z]);
	
	Cursor c = db.ssMonthlyAvgPerc();
	if (c.moveToFirst()) {
		do {
			try {

				String s = dateformat.format(dateformat.parse(c.getString(0)));
				dates.get(0)[i] = i;
				dates.get(1)[i] = i;
				renderer.addTextLabel(i, s);
				calcAvPerc(c.getString(1),c.getString(2),c.getString(3));
				values.get(0)[i] = percOfStrikes;
				values.get(1)[i] = percOfSpares;
				i++;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
			// DisplayTitle(c4);
		} while (c.moveToNext());
	}
	db.close();
	
	
    
    int length = renderer.getSeriesRendererCount();
    for (i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    setChartSettings(renderer, "Strikes and Spares average", "Month", "Percent", -0.5, z, 0, 100,
        Color.LTGRAY, Color.LTGRAY);
    renderer.setXLabels(0);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);


    Intent intent = ChartFactory.getLineChartIntent(context, buildDataset(titles, dates, values),
        renderer, "Average temperature");
    return intent;
  }
  
  public void calcAvPerc(String strikes, String spares, String frames){
	  if(strikes != null && "0".equals(strikes) != true ){
			percOfStrikes = ((double)Integer.parseInt(strikes))/Integer.parseInt(frames)*100;
			} else{
				percOfStrikes = 0.0;
			}
	  
	  if(spares != null && "0".equals(spares) != true ){
			percOfSpares = ((double)Integer.parseInt(spares))/Integer.parseInt(frames)*100;
			} else{
				percOfSpares = 0.0;
			}
	  
	  
  }

}