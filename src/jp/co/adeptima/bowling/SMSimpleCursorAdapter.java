package jp.co.adeptima.bowling;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class SMSimpleCursorAdapter extends SimpleCursorAdapter{
	
	Cursor c;
	Context context;
	Activity activity;
	EditEntries dbDel;
	DBAdapter dbAdapter;
	String idTag;
	public SMSimpleCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
        super(context, layout, c, from, to);
        
        this.c = c;
        this.context=context;
        this.activity=(Activity) context;
        this.dbDel = (EditEntries) context;
        this.dbAdapter = new DBAdapter(context);
        
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = View.inflate(context, R.layout.entries_list_item, null);
        View row = convertView;
        
        c.moveToPosition(position);
        
        TextView score = (TextView) convertView.findViewById(R.id.title);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        
        
        idTag = c.getString(0);
        

        time.setText(c.getString(2));
        score.setText(c.getString(1));
        
        
        ImageButton delButton = (ImageButton) convertView.findViewById(R.id.delButton);
        delButton.setFocusable(true);
        delButton.setClickable(true);
        delButton.setTag(idTag);
        delButton.setOnClickListener(new OnClickListener() {
        	 @Override
        	 public void onClick(View view) {
        		 String url = (String) view.getTag();
        		 dbAdapter.delRow(Integer.parseInt(url));
        		 dbDel.reload();
        	 }
        	 });

        return(row);
    }

}