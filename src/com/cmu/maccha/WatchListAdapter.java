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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class WatchListAdapter extends ArrayAdapter<HashMap<String, String>> {
	
	private ArrayList<HashMap<String, String>> data;
    private Activity activity;
    private LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public WatchListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
    	super(a, R.layout.list_row,d);
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    @Override
    public HashMap<String, String> getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        	
//            vi = inflater.inflate(R.layout.list_row, null);
        vi = inflater.inflate(R.layout.list_row, parent, false);
        	
//        	vi = LayoutInflater.from(activity).inflate(R.layout.list_row, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView status = (TextView)vi.findViewById(R.id.status); // artist name
        TextView highestBid = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
 
        name.setText(item.get(WatchlistActivity.KEY_NAME));
        
        Log.d("TAG", "NAME: " +item.get(WatchlistActivity.KEY_NAME));
       // status.setText(item.get(ActiveBidsActivity.KEY_STATUS));
        String stat = item.get(WatchlistActivity.KEY_STATUS);
        String expired = item.get(WatchlistActivity.KEY_EXPIREDATE);
        expired = expired.substring(0,10) + " " + expired.substring(11,expired.length()-2);
        
        String starttime = item.get(WatchlistActivity.KEY_STARTTIME);
        starttime = starttime.substring(0,10) + " " + starttime.substring(11,starttime.length()-2);
        
        System.out.println("pringing starttime.." +starttime);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("current time.." +dateFormat.format(date));
        

        Date expdate = null;
        Date stdate = null;

        
        try {
			expdate = parseDate(expired, "yyyy-MM-dd HH:mm:ss");
			stdate = parseDate(starttime, "yyyy-MM-dd HH:mm:ss");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        //get current date time with Calendar()
 	   Calendar cal = Calendar.getInstance();
// 	   System.out.println(dateFormat.format(cal.getTime()));
 	   Log.d("TAG","CURRENT DATE: " + dateFormat.format(cal.getTime()) + "EXPIRE DATE: " +dateFormat.format(expdate) + 
 			   "START DATE: " +dateFormat.format(stdate));
 	  
 	  int results = date.compareTo(stdate);
 	 Log.d("TAG","time cmpare:  "+results);// -1 means first before secnd..not started
 	   String highest = item.get(WatchlistActivity.KEY_HIGHESTPRICE);
 	  Log.d("TAG", "current highest price: "+highest);
 	  // String mycurrprice = item.get(ActiveBidsActivity.KEY_MYPRICE);
 	 
 	  	
 	  //	double mycurr = Double.parseDouble(mycurrprice.trim());
 	  	
 	  	if (results<0){//start date is after today
 	  		status.setText("Not started");
	    	status.setTextColor(Color.BLUE);
 	  	}
 	  	else if (results>=0){//start date before or on today
	 	    if(expdate.after(date)){//time left for bid
	 	    	
	 	    	status.setText("Current");
	 	    	 status.setTextColor(Color.parseColor("#3B5323"));
	 	      
	 	     /* if (mycurr>=high){
	 	    	 status.setText("Winning :)");
	 	    	 status.setTextColor(Color.parseColor("#3B5323"));
	 	      }
	 	      
	 	      else if (mycurr<high){
	 	    	 status.setText("Losing :(");
	 	    	 status.setTextColor(Color.parseColor("#FF5721"));
	 	      }*/
	 	
	 	    }
	 	    
	 	    else {
	 	      System.out.println("expdate is before today.");
	 	      status.setText("Expired");
	 	      status.setTextColor(Color.RED);
	 	    }
 	  	}
	 	    
 	   try{
 		  double high = Double.parseDouble(highest.trim());
 		 highestBid.setText("$"+item.get(WatchlistActivity.KEY_HIGHESTPRICE) + "0");
 	   }
 	   catch(Exception e){
 		  highestBid.setText("No bids");
 	   }
        
 	   System.out.println("highest bid is: " +item.get(WatchlistActivity.KEY_HIGHESTPRICE));
        
        imageLoader.DisplayImage("http://10.0.2.2:3000"+item.get(WatchlistActivity.KEY_THUMB_URL), thumb_image);
        
     
        return vi;
    }
    
    
    private void setWatchListTab(HashMap<String, String> item, TextView name,
			TextView status, TextView highestBid, ImageView thumb_image) {
		
        name.setText(item.get(WatchlistActivity.KEY_NAME));
        
        Log.d("TAG", "STATUS: " +item.get(WatchlistActivity.KEY_STATUS));
       // status.setText(item.get(ActiveBidsActivity.KEY_STATUS));
        String stat = item.get(WatchlistActivity.KEY_STATUS);
        String expired = item.get(WatchlistActivity.KEY_EXPIREDATE);
        expired = expired.substring(0,10) + " " + expired.substring(11,expired.length()-2);
        
        String starttime = item.get(WatchlistActivity.KEY_STARTTIME);
        starttime = starttime.substring(0,10) + " " + expired.substring(11,expired.length()-2);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("current time.." +dateFormat.format(date));
        

        Date expdate = null;
        Date stdate = null;

        
        try {
			expdate = parseDate(expired, "yyyy-MM-dd HH:mm:ss");
			stdate = parseDate(expired, "yyyy-MM-dd HH:mm:ss");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        //get current date time with Calendar()
 	   Calendar cal = Calendar.getInstance();
// 	   System.out.println(dateFormat.format(cal.getTime()));
 	   Log.d("TAG","CURRENT DATE: " + dateFormat.format(cal.getTime()) + "EXPIRE DATE: " +dateFormat.format(expdate) + 
 			   "START DATE: " +dateFormat.format(stdate));
 	  
 	  int results = date.compareTo(stdate);
 	 Log.d("TAG","time cmpare:  "+results);// -1 means first before secnd..not started
 	   String highest = item.get(WatchlistActivity.KEY_HIGHESTPRICE);
 	  Log.d("TAG", "current highest price: "+highest);
 	  // String mycurrprice = item.get(ActiveBidsActivity.KEY_MYPRICE);
 	 
 	  	
 	  //	double mycurr = Double.parseDouble(mycurrprice.trim());
 	  	
 	  	//if (results<0){//start date is after today
 	  		//status.setText("Not started");
	    	//status.setTextColor(Color.BLUE);
 	  	//}
 	  //	else if (results>=0){//start date before or on today
	 	    if(expdate.after(date)){//time left for bid
	 	    	
	 	    	status.setText("Current");
	 	    	 status.setTextColor(Color.YELLOW);
	 	      
	 	     /* if (mycurr>=high){
	 	    	 status.setText("Winning :)");
	 	    	 status.setTextColor(Color.parseColor("#3B5323"));
	 	      }
	 	      
	 	      else if (mycurr<high){
	 	    	 status.setText("Losing :(");
	 	    	 status.setTextColor(Color.parseColor("#FF5721"));
	 	      }*/
	 	
	 	    }
	 	    
	 	    else {
	 	      System.out.println("expdate is before today.");
	 	      status.setText("Expired");
	 	      status.setTextColor(Color.RED);
	 	    }
 	  //	}
	 	    
 	  /* try{
 		  double high = Double.parseDouble(highest.trim());
 		 highestBid.setText("$"+item.get(WatchlistActivity.KEY_HIGHESTPRICE) + "0");
 	   }
 	   catch(Exception e){
 		  highestBid.setText("No bids");
 	   }*/
        
 	   System.out.println("highest bid is: " +item.get(WatchlistActivity.KEY_HIGHESTPRICE));
        
        imageLoader.DisplayImage("http://10.0.2.2:3000"+item.get(WatchlistActivity.KEY_THUMB_URL), thumb_image);
//        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
        
//        imageLoader.DisplayImage("http://10.0.2.2:3000"+"/system/items/pictures/000/000/004/original/Autumn.jpg?1354124701", thumb_image);
		
	}

	private void setActiveOrdersTab( HashMap<String, String> item, TextView name,TextView status, TextView highestBid, ImageView thumb_image ) {
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
        

        Date expdate = null;

        
        try {
			expdate = parseDate(expired, "yyyy-MM-dd HH:mm:ss");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        //get current date time with Calendar()
 	   Calendar cal = Calendar.getInstance();
// 	   System.out.println(dateFormat.format(cal.getTime()));
 	   Log.d("TAG","CURRENT DATE: " + dateFormat.format(cal.getTime()) + "EXPIRE DATE: " +dateFormat.format(expdate));
 	  
 	  int results = date.compareTo(expdate);
 	 Log.d("TAG","time cmpare:  "+results);// -1 means first before secnd..not expired
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
 	
 	    }
 	    else {
 	      System.out.println("expdate is before today.");
 	      status.setText("Expired");
 	      status.setTextColor(Color.RED);
 	    }
 	   
        
 	   System.out.println("highest bid is: " +item.get(ActiveBidsActivity.KEY_HIGHESTPRICE));
        highestBid.setText("$"+item.get(ActiveBidsActivity.KEY_HIGHESTPRICE) + "0");
        imageLoader.DisplayImage("http://10.0.2.2:3000"+item.get(ActiveBidsActivity.KEY_THUMB_URL), thumb_image);
//        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
        
//        imageLoader.DisplayImage("http://10.0.2.2:3000"+"/system/items/pictures/000/000/004/original/Autumn.jpg?1354124701", thumb_image);
		
	}

	private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
