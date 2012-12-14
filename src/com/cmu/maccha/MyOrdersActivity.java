package com.cmu.maccha;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrdersActivity extends Activity {
	static final String TAG = "MACCHA";
	private ArrayList<String> itemsToAdd;
	private  ArrayList<HashMap<String, String>> itemsList;
	private ArrayList<String> addedNames;
	private final String localURL = MainActivity.localURL;//"128.237.212.48";//"10.0.0.11";//"128.237.134.67";
	private final String userNum = MainActivity.userNum;
	
	//XML node keys
	static final String KEY_NAME = "name";
	static final String KEY_PRICE = "price";//float
	static final String KEY_STATUS = "status";//int
	static final String KEY_DESC = "description";
	static final String KEY_CATEGORY = "category";
	static final String KEY_CONDITION = "condition";
	static final String KEY_LOCATION = "location";
	static final String KEY_SELLERNAME = "seller_id"; //int
	static final String KEY_EXPIREDATE = "end-time"; //datetime
	static final String KEY_THUMB_URL= "picture-url";
	static final String KEY_MYPRICE= "my-bid-price";
	static final String KEY_HIGHESTPRICE= "max-bid";

	ListView list;
    MyOrdersAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_bids);
        
        MyCha.currentTab = 3;
        
        Log.d("TAG", "I got to MYORDERS activity.." +MyCha.currentTab  );
        addedNames = new ArrayList<String>();
        
        itemsList = new ArrayList<HashMap<String, String>>();
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
          }
        
        
	        	
	        	    	try {
							updateBids("seethaa@cmu.edu");
						} catch (Exception e) {
							Log.d("TAG", "user can't be found.");
							e.printStackTrace();
						}
	        
	        	
	        	Log.d("TAG", "I got past that thread.");

	        	list=(ListView)findViewById(R.id.list);

	        	// Getting adapter by passing xml data ArrayList
	        	adapter=new MyOrdersAdapter(this, itemsList);
	        	list.setAdapter(adapter);

	        	// Click event for single list row
	        	list.setOnItemClickListener(new OnItemClickListener() {

	        		@Override
	        		public void onItemClick(AdapterView<?> parent, View view,
	        				int position, long id) {

	        		}
	        	});		
	        	
	        	this.adapter.notifyDataSetChanged();
    }

    @Override
  	protected void onResume()
  	{
  		super.onResume();
  		updateBids("seethaa@cmu.edu");
		this.adapter.notifyDataSetChanged();
  	
  	}

  	@Override
  	protected void onPause()
  	{

  		super.onPause();
  		updateBids("seethaa@cmu.edu");
		this.adapter.notifyDataSetChanged();
  		
  	}

  	@Override
  	protected void onStop()
  	{
  		super.onStop();
  		updateBids("seethaa@cmu.edu");
		this.adapter.notifyDataSetChanged();
  		
  	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_orders, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    private void updateBids(String username ){
//    	String URL = "http://10.0.2.2:3000/api/users/1/transactions.xml";
    	String URL = "http://"+localURL+":3000/api/users/"+userNum+"/transactions.xml";
    	  XMLParser parser = new XMLParser();
          String xml = parser.getXmlFromUrl(URL); // getting XML from URL
          System.out.println(xml);
          Document doc = parser.getDomElement(xml); // getting DOM element
   
          NodeList nl = doc.getElementsByTagName("transaction");
          // looping through all song nodes &lt;song&gt;
          for (int i = 0; i < nl.getLength(); i++) {
        		 // creating new HashMap
	            HashMap<String, String> map = new HashMap<String, String>();
	            Element e = (Element) nl.item(i);
	            
	           
	            
	            String newName = parser.getValue(e, KEY_NAME);
	            if (!addedNames.contains(newName)){
	            	addedNames.add(newName);
		            		
		            // adding each child node to HashMap key =&gt; value
		            map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
		            map.put(KEY_PRICE, parser.getValue(e, KEY_PRICE));
		            map.put(KEY_STATUS, parser.getValue(e, KEY_STATUS));
		            map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
		            map.put(KEY_CATEGORY, parser.getValue(e, KEY_CATEGORY));
		            map.put(KEY_CONDITION, parser.getValue(e, KEY_CONDITION));
		            map.put(KEY_LOCATION, parser.getValue(e, KEY_LOCATION));
		            map.put(KEY_SELLERNAME, parser.getValue(e, KEY_SELLERNAME));
		            map.put(KEY_MYPRICE, parser.getValue(e, KEY_MYPRICE));
		            map.put(KEY_HIGHESTPRICE, parser.getValue(e, KEY_HIGHESTPRICE));
		            map.put(KEY_EXPIREDATE, parser.getValue(e, KEY_EXPIREDATE));
		            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
		 
		        
		            // adding HashList to ArrayList
		            itemsList.add(map);
	            }
          }
    }

}
