package jp.co.adeptima.bowling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainwindow);
	    // TODO Auto-generated method stub
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
