package jp.co.adeptima.bowling;



import static android.provider.BaseColumns._ID;
import static jp.co.adeptima.bowling.Constants.SCORE_COLUMN;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


public class EditEntries extends ListActivity  {
	Cursor cursor;
	DBAdapter db = new DBAdapter(this);
	private static String[] FROM = { _ID, SCORE_COLUMN, "date(time, 'localtime')", };
	private static int[] TO = { R.id.rowid, R.id.title, R.id.time,  };
	public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.entries_list);
	      try {
	    	  cursor = getEvents();
	    	  
	          showEvents(cursor);
	       } finally {
	          db.close();
	          }
	   }
	

	
	 public void showEvents(Cursor cursor) {
	      // Set up data binding
	       SMSimpleCursorAdapter adapter = new SMSimpleCursorAdapter(this,
	            R.layout.entries_list_item, cursor, FROM, TO);
	      setListAdapter(adapter);
	      adapter.notifyDataSetChanged();

	      
	   }
	 
	 private Cursor getEvents() {
		  cursor = db.getAllScores();
		  startManagingCursor(cursor);
	      return cursor;
	   }
	 	
		public void reload() {
		    Intent intent = getIntent();
		    overridePendingTransition(0, 0);
		    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    finish();
		    overridePendingTransition(0, 0);
		    startActivity(intent);
		}


}
