package com.cmu.maccha;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView status = (TextView)vi.findViewById(R.id.status); // artist name
        TextView highestBid = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
 
        // Setting all values in listview
      
	        Log.d("TAG", "NAME: " +item.get(ActiveBidsActivity.KEY_NAME));
	        name.setText(item.get(ActiveBidsActivity.KEY_NAME));
	        
	        Log.d("TAG", "STATUS: " +item.get(ActiveBidsActivity.KEY_STATUS));
	       // status.setText(item.get(ActiveBidsActivity.KEY_STATUS));
	        String stat = item.get(ActiveBidsActivity.KEY_STATUS);
	        String expired = item.get(ActiveBidsActivity.KEY_EXPIREDATE);
	        expired = expired.substring(0,10) + " " + expired.substring(11,expired.length()-2);
	        
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	        System.out.println("current time.." +dateFormat.format(date));
	        
	        SimpleDateFormat sdfgmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        sdfgmt.setTimeZone(TimeZone.getTimeZone("EST"));
	        
	        
	    
	       
	        Date expdate = null;
	        Date newExpDate = null;
	        Date inptdate = null;
	        
	        try {
				expdate = parseDate(expired, "yyyy-MM-dd HH:mm:ss");
				
				
//				newExpDate = dateFormat.format(shiftTimeZone(expdate, TimeZone.getTimeZone("GMT"), TimeZone.getTimeZone("EST")));
				inptdate = sdfgmt.parse(expired);
//				System.out.println("EST:\t\t" + sdfgmt.format(expdate));
				Log.d("TAG","EXPIRED DATE: " + dateFormat.format(expdate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        DateFormat estFormat = new SimpleDateFormat();
	        DateFormat gmtFormat = new SimpleDateFormat();
	        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
	        TimeZone estTime = TimeZone.getTimeZone("EST");
	        estFormat.setTimeZone(gmtTime);
	        gmtFormat.setTimeZone(estTime);
//	        System.out.println("GMT Time: " + estFormat.format(expdate));
//	        System.out.println("EST Time: " + gmtFormat.format(expdate));
	        
	        Date newExp = null;
	        
	     
	        
	        //get current date time with Calendar()
	 	   Calendar cal = Calendar.getInstance();
//	 	   System.out.println(dateFormat.format(cal.getTime()));
	 	   Log.d("TAG","CURRENT DATE: " + dateFormat.format(cal.getTime()));
	 	  
	 	  int results = date.compareTo(expdate);
	 	   String highest = item.get(ActiveBidsActivity.KEY_HIGHESTPRICE);
	 	   String mycurrprice = item.get(ActiveBidsActivity.KEY_MYPRICE);
	 	   
	 	  	double high = Double.parseDouble(highest.trim());
	 	  	double mycurr = Double.parseDouble(mycurrprice.trim());
	 	  	
	 	    if(expdate.after(date)){//
	 	    	
	 	      System.out.println("Current transaction. My current price is "+mycurr + " and highest bid is: "+high);
	 	      
	 	      if (mycurr>=high){
	 	    	 status.setText("Winning :)");
	 	    	 status.setTextColor(Color.parseColor("#3B5323"));
	 	      }
	 	      
	 	      else if (mycurr<high){
	 	    	 status.setText("Losing :(");
	 	    	 status.setTextColor(Color.parseColor("#FF5721"));
	 	      }
	 	  /*   if (stat.equals("1")){
		        	status.setText("Winning :)");
		        	status.setTextColor(Color.parseColor("#3B5323"));
		        }
		        else if (stat.equals("2")){
		        	status.setText("Losing :(");
		        	status.setTextColor(Color.parseColor("#FF5721"));

		        }*/
	 	    }
	 	    else {
	 	      System.out.println("expdate is before today.");
	 	      status.setText("Expired");
	 	      status.setTextColor(Color.RED);
	 	    }
	 	   
	        
	 	   System.out.println("highest bid is: " +item.get(ActiveBidsActivity.KEY_HIGHESTPRICE));
	        highestBid.setText("$"+item.get(ActiveBidsActivity.KEY_HIGHESTPRICE));
	        imageLoader.DisplayImage("http://10.0.2.2:3000"+item.get(ActiveBidsActivity.KEY_THUMB_URL), thumb_image);
//	        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
	        
//	        imageLoader.DisplayImage("http://10.0.2.2:3000"+"/system/items/pictures/000/000/004/original/Autumn.jpg?1354124701", thumb_image);
     
        return vi;
    }
    
    private Date shiftTimeZone(Date date, TimeZone sourceTimeZone, TimeZone targetTimeZone) {
        Calendar sourceCalendar = Calendar.getInstance();
        sourceCalendar.setTime(date);
        sourceCalendar.setTimeZone(sourceTimeZone);

        Calendar targetCalendar = Calendar.getInstance();
        for (int field : new int[] {Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
            targetCalendar.set(field, sourceCalendar.get(field));
        }
        targetCalendar.setTimeZone(targetTimeZone);

        return targetCalendar.getTime();
    }
    
    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
