package jp.co.adeptima.bowling;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

	
	public static final String STRIKES_COLUMN = "strikes";
	public static final String SPARES_COLUMN = "spares";
	public static final String FRAMES_COLUMN = "strOpps";
	public static final String SCORE_COLUMN = "score";
	public static final String TIMESTAMPT_COLUMN = "time";
	public static final String TIMESTAMP2 = "date(time, 'localtime')";
	public static final String DATABASE_TABLE = "scores";
	public static final String DATABASE_NAME = "bowling_scores.db";
}