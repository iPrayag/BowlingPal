package jp.co.adeptima.bowling;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int temp = 0;
	DecimalFormat df = new DecimalFormat("#.##");
	DBAdapter db = new DBAdapter(this);
	String average;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainwindow);
	    TextView avtext = (TextView) findViewById(R.id.average);
	    
		Cursor c3 = db.getAverage();
		if (c3.moveToFirst()) {
			average = c3.getString(0);
			try{
			temp = (int)Double.parseDouble(average);
			} catch(NullPointerException e) 
			{
				temp = 0;
			}
			
		
		}
		db.close();
		avtext.setText(Integer.toString(temp));
	    // TODO Auto-generated method stub
	}
	
	@Override
	public void onResume() {
		super.onResume();
	    TextView avtext = (TextView) findViewById(R.id.average);
	    
		Cursor c3 = db.getAverage();
		if (c3.moveToFirst()) {
			average = c3.getString(0);
			try{
			temp = (int)Double.parseDouble(average);
			} catch(NullPointerException e) 
			{
				temp = 0;
			}
		}
		db.close();
		avtext.setText(Integer.toString(temp));
	}
	
	public void openScoreActivity(View view) {
		Intent intent = new Intent(this, bowling.class);
		startActivity(intent);
	}
	
	public void statsActivity(View view) {
		Intent intent = new Intent(this, Stats.class);
		startActivity(intent);
	}
	
	public void launchStuff(){
		Toast.makeText(this, "some of the fields are empty",
				Toast.LENGTH_SHORT).show();
	}
	
	

}
