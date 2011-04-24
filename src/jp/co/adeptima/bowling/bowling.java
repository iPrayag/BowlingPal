package jp.co.adeptima.bowling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;



import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	private Spinner m_myDynamicSpinner;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	String snumber;
	boolean spin1Check;
	boolean spin3Check;
	Object strikes = 0;
	Object spares = 0;
	Object frames = 0;
	int possibleSpares;
	int modifier;
	private IChart[] mCharts = new IChart[] {new ProjectStatusChart(),new SalesStackedBarChart()};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext1.addTextChangedListener(this);
		final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
		final Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
		final TextView strikesLabel = (TextView)findViewById(R.id.strikesLabel);
		final TextView sparesLabel = (TextView)findViewById(R.id.sparesLabel);
		spin1Check = false;
		spin3Check = false;
		

		populateStrikesSpinner(spinner3, 12, 10);

		strikesLabel.setVisibility(View.INVISIBLE);
		
		spinner1.setVisibility(View.INVISIBLE);
		spinner2.setVisibility(View.INVISIBLE);
		sparesLabel.setVisibility(View.INVISIBLE);
		
		
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	frames = parentView.getItemAtPosition(position);
		    	if(spin3Check){
		
		    		spinner1.setVisibility(View.VISIBLE);
		    		strikesLabel.setVisibility(View.VISIBLE);
			    	populateStrikesSpinner(spinner1, Integer.parseInt(frames.toString()), 0);
			    	
		    } else {
		    	spin3Check = true; 
		    }
		    	}

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
		
		
		
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	strikes = parentView.getItemAtPosition(position);
		    	if(spin1Check){
		    	if(Integer.parseInt(strikes.toString()) <=11  ){
		    		modifier = 2;	   		
		    	}
		    	else{
		    		modifier = 0;
		    	}
		    	if(Integer.parseInt(strikes.toString()) == 12 )
		    	{
		    		spinner2.setVisibility(View.INVISIBLE);
			    	sparesLabel.setVisibility(View.INVISIBLE);
		    	}
		    	else{
		    		spinner2.setVisibility(View.VISIBLE);
			    	sparesLabel.setVisibility(View.VISIBLE);
		    	}
		    	
		    	populateStrikesSpinner(spinner2, modifier+10-Integer.parseInt(strikes.toString()), 0);	
		    	
		    } else {
		    	spin1Check = true; 
		    }
		    	}

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	spares = parentView.getItemAtPosition(position);
		    	}

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
	     
	}

	public void addScoreButton(View view) {
		
		if (edittext1.getText().toString().equals("")) {
			Toast.makeText(bowling.this, "some of the fields are empty",
					Toast.LENGTH_SHORT).show();
		} else {
			int sc1 = Integer.parseInt(edittext1.getText().toString());
			id = db.insertScore(sc1, Integer.parseInt(strikes.toString()), Integer.parseInt(spares.toString()),Integer.parseInt(frames.toString()));
//			Cursor c3 = db.getAllScores();
//			if (c3.moveToFirst()) {
//				average = c3.getString(4);
//			}
			db.close();
			Toast.makeText(bowling.this, "Added",
					Toast.LENGTH_SHORT).show();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
			edittext1.setText("");
		}

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
	
	public void populateStrikesSpinner(Spinner spinner, int max, int min){
		//Strikes spinner
		m_myDynamicSpinner = spinner;
		m_adapterForSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
		m_adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		m_myDynamicSpinner.setAdapter(m_adapterForSpinner);
		for(int i=min; i<max+1; i++){
			m_adapterForSpinner.add(Integer.toString(i));
       }
		
		
		
	}

}