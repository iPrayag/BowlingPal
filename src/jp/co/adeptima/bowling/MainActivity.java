package jp.co.adeptima.bowling;


import java.text.DecimalFormat;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	int temp = 0;
	DecimalFormat df = new DecimalFormat("#.##");
	DBAdapter db = new DBAdapter(this);
	String average;
	TextView avtext;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainwindow);	   
	    
	    avtext = (TextView) findViewById(R.id.average); 
	    getAverage();
		avtext.setText(Integer.toString(temp));
	    // TODO Auto-generated method stub
	}
	
	@Override
	public void onResume() {
		super.onResume();
	    getAverage();
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
	
	public void manageDb(View view) {
		Intent intent = new Intent(this, DatabaseBackups.class);
		startActivity(intent);
	}
	
	public void helpActivity(View view) {
		Intent intent = new Intent(this, Help.class);
		startActivity(intent);
	}
	
	
	

	
	public void getAverage(){
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
	}
	

	
	

}
