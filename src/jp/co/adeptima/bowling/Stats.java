package jp.co.adeptima.bowling;

import java.text.DecimalFormat;

import jp.co.adeptima.bowling.charts.DailyAverageLinear;
import jp.co.adeptima.bowling.charts.MonthlyAverageLinear;
import jp.co.adeptima.bowling.charts.IChart;
import jp.co.adeptima.bowling.charts.SSMonthlyAverageLinear;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Stats extends Activity {

	private IChart[] mCharts = new IChart[] {new MonthlyAverageLinear(), new DailyAverageLinear(),
			new SSMonthlyAverageLinear()};
	DBAdapter db = new DBAdapter(this);
	String totalRounds = null;
	String numberOfStrikes = null;
	String numberOfSpares = null;
	String numberOfFrames = null;
	String over200 = null;

	Alert alert = new Alert(this);
	TextView avtext;
	TextView strikesPerc;
	TextView sparesPerc;
	
	TextView totalStrikes;
	TextView totalSpares;
	TextView totalEmptyFrames;
	TextView over200perc;
	TextView hs;
	
	
	double percOfStrikes = 0.0;
	double percOfSpares = 0.0;
	double percOver200 = 0.0;

	DecimalFormat df = new DecimalFormat("#.##");
	int openFrames = 0;
	String highScore = "0";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		avtext = (TextView) findViewById(R.id.totalRoundsStat);
		strikesPerc = (TextView) findViewById(R.id.percentOfStrikes);
		sparesPerc = (TextView) findViewById(R.id.percentOfSpares);
		
		totalStrikes = (TextView) findViewById(R.id.totalStrikes);
		totalSpares = (TextView) findViewById(R.id.totalSpares);
		totalEmptyFrames = (TextView) findViewById(R.id.emptyFrames);
		over200perc = (TextView) findViewById(R.id.over200perc);
		hs = (TextView) findViewById(R.id.hsValue);
		df.format(percOfStrikes);	
	}

	public void onResume() {
		super.onResume();
		getData();
		calcPercentages();
		avtext.setText(totalRounds);
		strikesPerc.setText(df.format(percOfStrikes)+"%");
		sparesPerc.setText(df.format(percOfSpares)+"%");
		
		totalStrikes.setText(numberOfStrikes);
		totalSpares.setText(numberOfSpares);
		totalEmptyFrames.setText(Integer.toString(openFrames));
		over200perc.setText(df.format(percOver200)+"%");
		hs.setText(highScore);
	}

	public void dailyAverageGraph(View view) {
		Cursor c = db.getNumberOfScores();
		if (c.moveToFirst()) {
			if (Integer.parseInt(c.getString(0)) != 0) {
				Intent intent = null;
				intent = mCharts[1].execute(this);
				startActivity(intent);
			} else {
				alert.showAlert();
			}
		}

		db.close();
	}

	public void monthlyAverageGraph(View view) {
		Cursor c = db.getNumberOfScores();
		if (c.moveToFirst()) {
			if (Integer.parseInt(c.getString(0)) != 0) {
				Intent intent = null;
				intent = mCharts[0].execute(this);
				startActivity(intent);
			} else {
				alert.showAlert();
			}
		}

		db.close();
	}
	
	public void monthlySSperc(View view) {
		Cursor c = db.getNumberOfScores();
		if (c.moveToFirst()) {
			if (Integer.parseInt(c.getString(0)) != 0) {
				Intent intent = null;
				intent = mCharts[2].execute(this);
				startActivity(intent);
			} else {
				alert.showAlert();
			}
		}

		db.close();
	}
	
	


	
	
	public void getData(){
		Cursor c = db.getPercentage();
		if (c.moveToFirst()) {
			totalRounds = c.getString(0);
			numberOfStrikes = c.getString(1);
			numberOfSpares = c.getString(2);
			numberOfFrames = c.getString(3);
		}
		db.close();
		Cursor c2 = db.gamesOver200();
		if (c2.moveToFirst()) {
			over200 = c2.getString(0);
		}
		db.close();
		Cursor c3 = db.getHighScore();
		if (c3.moveToFirst() && c3.getString(0) != null) {
			highScore = c3.getString(0);
		}
		db.close();
		
		
	}
	
	public void calcPercentages(){
		if(numberOfStrikes != null && "0".equals(numberOfStrikes) != true ){
			percOfStrikes = ((double)Integer.parseInt(numberOfStrikes))/Integer.parseInt(numberOfFrames)*100;
			}
		
			if(numberOfSpares != null && "0".equals(numberOfSpares) != true){
			percOfSpares = ((double)Integer.parseInt(numberOfSpares)/Integer.parseInt(numberOfFrames))*100;
			}
			
			if(over200 != null && "0".equals(over200) != true){
				percOver200 = ((double)Integer.parseInt(over200)/Integer.parseInt(totalRounds))*100;
				}
			
			if(numberOfStrikes != null && numberOfSpares != null){
				openFrames = Integer.parseInt(numberOfFrames)-Integer.parseInt(numberOfStrikes)-Integer.parseInt(numberOfSpares);
			}
			
			if(numberOfStrikes == null){
				percOfStrikes = 0;
				numberOfStrikes = "0";
				numberOfSpares = "0";
			}

			

			
	}

}
