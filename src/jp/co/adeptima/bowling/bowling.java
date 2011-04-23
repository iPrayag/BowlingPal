package jp.co.adeptima.bowling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;



import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;


public class bowling extends Activity implements TextWatcher{
	
	TextView text;
	String averageScore;
	EditText edittext1;
	EditText edittext2;
	TextView avtext;
	long id;
	String average;
	String date;
	public static final String STORED_AVERAGE = "MyAverage";
	DBAdapter db = new DBAdapter(this);
	
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	String snumber;
	
	private IChart[] mCharts = new IChart[] {new ProjectStatusChart(),new SalesStackedBarChart()};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext1.addTextChangedListener(this);
		avtext = (TextView) findViewById(R.id.textView1);

		SharedPreferences settings = getSharedPreferences(STORED_AVERAGE, 0);
		String stav = settings.getString("averageScore", "Nothing here yet..");
		avtext.setText(stav);
		
		
		
	
	     
	}

	public void addScoreButton(View view) {
		
		if (edittext1.getText().toString().equals("")) {
			Toast.makeText(bowling.this, "some of the fields are empty",
					Toast.LENGTH_SHORT).show();
		} else {
			int sc1 = Integer.parseInt(edittext1.getText().toString());
			id = db.insertScore(sc1);
			Cursor c3 = db.getAverage();
			if (c3.moveToFirst()) {
				average = c3.getString(0);
			}
			db.close();

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
			edittext1.setText("");
			avtext.setText(average);

			// print confirmation
			//Toast.makeText(bowling.this, date, Toast.LENGTH_SHORT).show();

			// saving current high score to Shared Preferences
			SharedPreferences settings = getSharedPreferences(STORED_AVERAGE, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("averageScore", average);
			editor.commit();
			
			
			
			

		}

	}

	public void DisplayTitle(Cursor c) {
		Toast.makeText(
				this,
				"id: " + c.getString(0) + "\n" + "Sore: " + c.getString(1)
						+ "\n" + "Date: " + c.getString(2) + "\n",
				Toast.LENGTH_LONG).show();
	}

	// "Clear Score" button
	public void clearScore(View view) {
		db.deleteEverything();
		db.close();
		avtext.setText("Nothing here..");
		Toast.makeText(bowling.this, "Deleted", Toast.LENGTH_SHORT).show();
		SharedPreferences settings = getSharedPreferences(STORED_AVERAGE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
	
	public void dailyAverage(View view) {
		Intent intent = null;
		intent = mCharts[1].execute(this);
		startActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			String c = s.toString();
			if (Integer.parseInt(c) > 300 & c != "") {
				edittext1.setText("300");
			}
		} catch (NumberFormatException e) {
		} finally {
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

}