package com.cmu.maccha;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MyCha extends TabActivity {
//	private FragmentTabHost mTabHost;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cha);
        
        Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
 
		// Active bids tab
		Intent intentAndroid = new Intent().setClass(this, ActiveBidsActivity.class);
		TabSpec tabSpecAndroid = tabHost
		  .newTabSpec("Active Bids")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_activebids_config))
		  .setContent(intentAndroid);
 
		// My orders tab
		Intent intentApple = new Intent().setClass(this, MyOrdersActivity.class);
		TabSpec tabSpecApple = tabHost
		  .newTabSpec("My Orders")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_myorders_config))
		  .setContent(intentApple);
 
		// Watchlist tab
		Intent intentWindows = new Intent().setClass(this, WatchlistActivity.class);
		TabSpec tabSpecWindows = tabHost
		  .newTabSpec("Watchlist")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_watchlist_config))
		  .setContent(intentWindows);
 
		// Settings tab
		Intent intentBerry = new Intent().setClass(this, SettingsActivity.class);
		TabSpec tabSpecBerry = tabHost
		  .newTabSpec("Settings")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_settings_config))
		  .setContent(intentBerry);
 
		// add all tabs 
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecWindows);
		tabHost.addTab(tabSpecBerry);
 
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(2);
		
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_cha, menu);
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

}
