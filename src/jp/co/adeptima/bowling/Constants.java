package jp.co.adeptima.bowling;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

	public static final String SCORE_COLUMN = "score";
	public static final String TIME = "time";
	public static final String TIMESTAMP2 = "date(time, 'localtime')";
	public static final String DATABASE_TABLE = "scores";
}