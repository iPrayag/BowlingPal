package jp.co.adeptima.bowling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Stats extends Activity {
	private Cursor c;
	private IChart[] mCharts = new IChart[] { new ProjectStatusChart(),
			new SalesStackedBarChart() };
	DBAdapter db = new DBAdapter(this);
	String totalRounds = null;
	String numberOfStrikes = null;
	String numberOfSpares = null;
	String numberOfFrames = null;
	double percOfStrikes = 0.0;
	double percOfSpares = 0.0;
	Alert alert = new Alert(this);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		Cursor c = db.getPercentage();
		if (c.moveToFirst()) {
			totalRounds = c.getString(0);
			numberOfStrikes = c.getString(1);
			numberOfSpares = c.getString(2);
			numberOfFrames = c.getString(3);
		}
		db.close();
		

		if(numberOfStrikes != null && "0".equals(numberOfStrikes) != true ){
		percOfStrikes = ((double)Integer.parseInt(numberOfStrikes))/Integer.parseInt(numberOfFrames)*100;
		}
		if(numberOfSpares != null && "0".equals(numberOfSpares) != true){
		percOfSpares = ((double)Integer.parseInt(numberOfSpares)/Integer.parseInt(numberOfFrames))*100;
		}
		TextView avtext = (TextView) findViewById(R.id.totalRoundsStat);
		TextView strikesPerc = (TextView) findViewById(R.id.percentOfStrikes);
		TextView sparesPerc = (TextView) findViewById(R.id.percentOfSpares);
		avtext.setText(totalRounds);
		strikesPerc.setText(Double.toString(percOfStrikes)+"%");
		sparesPerc.setText(Double.toString(percOfSpares)+"%");

		// TODO Auto-generated method stub
	}

	public void onResume() {
		super.onResume();
		Cursor c = db.getNumberOfScores();
		if (c.moveToFirst()) {
			totalRounds = c.getString(0);
		}
		db.close();
		TextView avtext = (TextView) findViewById(R.id.totalRoundsStat);
		avtext.setText(totalRounds);
	}

	public void dailyAverageGraph(View view) {
		Intent intent = null;
		intent = mCharts[1].execute(this);
		startActivity(intent);
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

	public void editEntries(View view) {
		Intent intent = new Intent(this, EditEntries.class);
		startActivity(intent);
	}
	
	// "Clear Score" button
	public void clearScore(View view) {
		db.deleteEverything();
		db.close();
		Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
	}

}
