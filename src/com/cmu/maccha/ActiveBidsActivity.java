package com.cmu.maccha;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActiveBidsActivity extends Activity {
	static final String TAG = "MACCHA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is Android tab");
        setContentView(textview);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
          }
        
        
        	Thread t = new Thread(){
        	    public void run(){
        	    	try {
						updateBids("seethaa@cmu.edu");
					} catch (Exception e) {
						Log.d("TAG", "user can't be found.");
						e.printStackTrace();
					}
        	    }
        	};
        	t.start();
			
		
//        setContentView(R.layout.activity_active_bids);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_active_bids, menu);
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
    
    
	private void updateBids(String username) throws Exception
	{
//		String lat = Double.toString(latitude);
//		String lon = Double.toString(longitude);
//		String placereq = "https://maps.googleapis.com/maps/api/place/search/xml?location=" + lon + "," + lat + "&radius=8000&types=" + placeToFind + "&sensor=true&" + "key=" + this.googleApiKey;

		String getReq = "http://10.0.2.2:3000/api/users/1/bids.xml";
		String temp = "";

		String responseString = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(new HttpGet(getReq));
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() == HttpStatus.SC_OK)
		{
			Log.d("TAG", "SENT THE REQ");

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.getEntity().writeTo(out);
			out.close();
			responseString = out.toString();

			Log.d("TAG", responseString);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseString));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("bids");
//			placesToAdd.clear();

			
			
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Element element = (Element) nodes.item(i);

				NodeList name = element.getElementsByTagName("name");
				Element line = (Element) name.item(0);
				
				temp = (getCharacterDataFromElement(line));
				temp += ":\n";

				NodeList title = element.getElementsByTagName("picture-url");
				line = (Element) title.item(0);
				temp += (getCharacterDataFromElement(line));

				Log.d("TAG", temp);
//				placesToAdd.add(temp);
			}

		}
		else
		{
			// Closes the connection.
			response.getEntity().getContent().close();
			throw new IOException(statusLine.getReasonPhrase());
		}
		/*if (myAdapter == null)
		{
			this.myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.placesToAdd);
			this.listLocations.setAdapter(myAdapter);
		}

		myAdapter.notifyDataSetChanged();*/
	}
	
	/**
	 * Necessary method to parse xml file of string containing xml
	 */
	public static String getCharacterDataFromElement(Element e)
	{
		Node child = e.getFirstChild();
		if (child instanceof CharacterData)
		{
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

}
