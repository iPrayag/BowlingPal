package jp.co.adeptima.bowling;


import static android.provider.BaseColumns._ID;
import static jp.co.adeptima.bowling.Constants.DATABASE_TABLE;
import static jp.co.adeptima.bowling.Constants.TIME;
import static jp.co.adeptima.bowling.Constants.SCORE_COLUMN;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter 
{
	
    public static final String TIMESTAMP = "time";
    public static final String TIMESTAMP2 = "date(time, 'localtime')";
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "bowling_scores.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE =
    	"CREATE TABLE " + DATABASE_TABLE + 
    	" ("+ _ID + " INTEGER primary key autoincrement, " + SCORE_COLUMN +
    	" int not null,"+ TIME +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP, strikes int, spares int, frames int);";
        
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
 

	private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                              int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS scores");
            onCreate(db);
        }
    }    
  //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertScore(int score, int strikes, int spares, int frames) 
    {
    	open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(SCORE_COLUMN, score);
        initialValues.put("strikes", strikes);
        initialValues.put("spares", spares);
        initialValues.put("frames", frames);
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteScore(int rowId) 
    {
    	open();
        return db.delete(DATABASE_TABLE, _ID + 
        		"=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllScores() 
    {
    	open();
        return db.query(DATABASE_TABLE, new String[] {
        		_ID, 
        		SCORE_COLUMN,
        		TIMESTAMP2,
        		"strikes",
        		"spares",
        		"frames"}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular title---
    public Cursor getScore(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		_ID,
                		SCORE_COLUMN,}, 
                		_ID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor getNumberOfScores(){
    	open();
    	return db.rawQuery("SELECT Count(*) FROM scores", null);
    }
    
    public Cursor getAverage(){
    	open();
    	return db.rawQuery("SELECT AVG("+SCORE_COLUMN+") FROM "+DATABASE_TABLE+"", null);
    }
    
    public Cursor getDailyAverage(){
    	open();
    	return db.rawQuery("SELECT Count("+_ID+"),AVG("+SCORE_COLUMN+") FROM "+DATABASE_TABLE+" GROUP BY date(time, 'localtime')", null);
    }
    
    public Cursor getMonthlyAverage(){
    	open();
    	return db.rawQuery("SELECT strftime('%Y-%m', time), AVG("+SCORE_COLUMN+") FROM "+DATABASE_TABLE+" GROUP BY strftime('%m', time)", null);
    }
    
    public boolean deleteEverything() 
    {
    	open();
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }
    
	 void delRow(int rowId){
		 deleteScore(rowId);
		 close();
	 }
	 
	 
	 public Cursor getPercentage(){
	    	open();
	    	return db.rawQuery("SELECT count(*), SUM(strikes), SUM(spares), SUM(frames) FROM "+DATABASE_TABLE, null);
	    }

	 
	 
    
    //---updates a title---
    public boolean updateScore(long rowId, int score) 
    {
        ContentValues args = new ContentValues();
        args.put(SCORE_COLUMN, score);
        return db.update(DATABASE_TABLE, args, 
        		_ID + "=" + rowId, null) > 0;
    }
}