package com.cmu.maccha;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
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
//        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        
        Log.d("TAG", "NAME: " +song.get(ActiveBidsActivity.KEY_NAME));
        name.setText(song.get(ActiveBidsActivity.KEY_NAME));
        
        Log.d("TAG", "STATUS: " +song.get(ActiveBidsActivity.KEY_STATUS));
       // status.setText(song.get(ActiveBidsActivity.KEY_STATUS));
        status.setText("Default text.Replace");
        
//        duration.setText(song.get(ActiveBidsActivity.KEY_DURATION));
       // imageLoader.DisplayImage(song.get(ActiveBidsActivity.KEY_THUMB_URL), thumb_image);
        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
        
        
        return vi;
    }
}
