package ti.android.ble.sensortag;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SummaryView extends Fragment {
	
	private static final String TAG = "StartScreenView";
	public static SummaryView mInstance = null;
	
	public static View view;
	private TableLayout table_layout;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.i(TAG, "onCreateView");
        mInstance = this;
        
        view = inflater.inflate(R.layout.activity_summary, container, false);
        table_layout = (TableLayout) view.findViewById(R.id.tableLayout1);
        
        buildTable();
        updateImportantInfo();

        return view;
  }
    
    public void buildTable() {
    	SummaryDataSource sum = new SummaryDataSource(this.getActivity());
        sum.open();
        
        Cursor c = sum.getAllExerciseRecords();
        
        int rows = c.getCount();
        System.out.println("num rows: " + rows);
        int cols = c.getColumnCount();
        System.out.println("num cols: " + cols);
        
        c.moveToFirst();
        
        for (int i = 0; i < rows; i++) {

        	   TableRow row = new TableRow(this.getActivity());
        	   row.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
        	     LayoutParams.WRAP_CONTENT));

        	   // inner for loop
        	   for (int j = 0; j < cols; j++) {
        		   
	        	   if (j != 0 && j != 1) {
	
		        	    TextView tv = new TextView(this.getActivity());
		        	    System.out.println("tv " + tv);
		//        	    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		//        	      LayoutParams.WRAP_CONTENT));
		        	    LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
		        	    if (j == 7 || j == 8) {
		        	    	params = new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f);
		        	    }
		        	    tv.setGravity(Gravity.CENTER);
		        	    tv.setTextSize(12);
		        	    tv.setWidth(0);
		        	    tv.setLayoutParams(params);
		        	    tv.setText(c.getString(j));
		        	    tv.setBackground(getResources().getDrawable(R.drawable.cell_shape));
		        	    
		        	    
		        	    row.addView(tv);
	        	   }

        	   }
        	   System.out.println("table layout: " + table_layout);
        	   System.out.println(row);
//        	   table_layout.addView(row, new TableLayout.LayoutParams(
//        	              LayoutParams.WRAP_CONTENT,
//        	             LayoutParams.WRAP_CONTENT));
        	   table_layout.addView(row);
        	   c.moveToNext();
        	  }
        c.close();
        sum.close();
    }
    

	public void updateImportantInfo(){
			SummaryDataSource sum = new SummaryDataSource(this.getActivity());
			sum.open();
		  	Cursor c = sum.getAllExerciseRecords();
		  	
	    //update average reps per exercise
	    TextView repNumber = (TextView) view.findViewById(R.id.repNumber);
		repNumber.setText(String.valueOf(sum.getRep()));
		
		//update average average angle
	    TextView averageAngle = (TextView) view.findViewById(R.id.averageAngleNumber);
	    averageAngle.setText(String.valueOf(sum.getAverageAngle()));
	    System.out.println("average angle: " + sum.getAverageAngle());
	    System.out.println("average rep: " + sum.getRep());
		
		//update average delta angle
	    TextView deltaAngle = (TextView) view.findViewById(R.id.deltaAngleNumber);
	    if (sum.getAverageAngle() != 0) {
	    	deltaAngle.setText(String.valueOf(sum.getAverageAngle()-170));
	    } else {
	    	deltaAngle.setText(String.valueOf(0));
	    }
		
		//update quality counts
		TextView good = (TextView) view.findViewById(R.id.goodNumber);
		TextView ok = (TextView) view.findViewById(R.id.okNumber);
		TextView ni = (TextView) view.findViewById(R.id.needsImprovementNumber);
		good.setText(String.valueOf(sum.getGoodCount()));
		ok.setText(String.valueOf(sum.getOkCount()));
		ni.setText(String.valueOf(sum.getNeedsImprovementCount()));
		c.close();
		sum.close();
	}

}