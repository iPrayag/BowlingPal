package jp.co.adeptima.bowling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class DatabaseBackups extends Activity {
	DBAdapter db = new DBAdapter(this);
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.db_backups);
	
	    
	}
	
	public void exportDb(View view) {
		confirmDialog(R.string.export_warning, 0, true);
	}
	
	public void importDb(View view) {
		confirmDialog(R.string.import_warning, 1, true);
	}
	
	public void clearScore(View view) {
		confirmDialog(R.string.import_warning, 0, false);
	}
	
	public void backup(int mode){
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				
			File bpDir = new File("/sdcard/LazyBowling/");
			// have the object build the directory structure, if needed.
			bpDir.mkdirs();

			String currentDBPath = "\\data\\jp.co.adeptima.bowling\\databases\\bowling_scores.db";
			String backupDBPath = "\\LazyBowling\\bowling_scores.db";

			File currentDB = new File(data, currentDBPath);
			File sdDB = new File(sd, backupDBPath);
			
			switch (mode){
			case 0:
				if (currentDB.exists()) {
				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(sdDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
				} else {
					Toast.makeText(this, "There is no database to export from.",
							Toast.LENGTH_SHORT).show();
				}
			case 1:
				if (sdDB.exists()) {
					FileChannel src = new FileInputStream(sdDB).getChannel();
					FileChannel dst = new FileOutputStream(currentDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
					} else {
						Toast.makeText(this, "Can't find \\LazyBowling\\bowling_scores.db to import from.",
								Toast.LENGTH_SHORT).show();
					}
			}
			
			} 
			else {
				Toast.makeText(this, "There is something wrong with your's SD card. Maybe you haven't inserted it?",
						Toast.LENGTH_SHORT).show();
			}
		}catch (Exception e) {
			Toast.makeText(this, e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public void editEntries(View view) {
		Intent intent = new Intent(this, EditEntries.class);
		startActivity(intent);
	}
	
	public void confirmDialog(int warningMessage, final int mode, final boolean doBackup){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(this.getString(warningMessage))
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   if(doBackup)
		        	   {
		        		   backup(mode);
		        	   } else {
		        		   db.deleteEverything();
		        		   db.close();
		        	   }
		       			Toast.makeText(DatabaseBackups.this, "Done.",
		       				Toast.LENGTH_SHORT).show();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		builder.create();
		builder.show();
	}

}
