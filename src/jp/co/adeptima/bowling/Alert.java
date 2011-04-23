package jp.co.adeptima.bowling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert {
	Context context;
	
	public Alert(Context context){
		this.context = context;
	}
	
	public void showAlert(){
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Oh noes.");
		alertDialog.setMessage("You havent added any scores yet.");
		alertDialog.setButton("Oh my", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		    	  alertDialog.dismiss();
		 
		    } });
		alertDialog.show();
	}
}
