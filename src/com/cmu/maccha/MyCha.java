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
	private final String localURL = "128.237.134.67";
//	private FragmentTabHost mTabHost;
	
	static int currentTab = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cha);
        
        Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
 
		// Active bids tab
		Intent intentActive = new Intent().setClass(this, ActiveBidsActivity.class);
		TabSpec tabSpecActive = tabHost
		  .newTabSpec("Active Bids")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_activebids_config))
		  .setContent(intentActive);
 
		// My orders tab
		Intent intentOrders = new Intent().setClass(this, MyOrdersActivity.class);
		TabSpec tabSpecOrders = tabHost
		  .newTabSpec("My Orders")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_myorders_config))
		  .setContent(intentOrders);
 
		// Watchlist tab
		Intent intentWatchlist = new Intent().setClass(this, WatchlistActivity.class);
		TabSpec tabSpecWatchlist = tabHost
		  .newTabSpec("Watchlist")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_watchlist_config))
		  .setContent(intentWatchlist);
 
		// Settings tab
		Intent intentSettings = new Intent().setClass(this, SettingsActivity.class);
		TabSpec tabSpecSettings = tabHost
		  .newTabSpec("Settings")
		  .setIndicator("", ressources.getDrawable(R.drawable.icon_settings_config))
		  .setContent(intentSettings);
 
		// add all tabs 
		tabHost.addTab(tabSpecActive);
		tabHost.addTab(tabSpecOrders);
		tabHost.addTab(tabSpecWatchlist);
		tabHost.addTab(tabSpecSettings);
 
		//set Watchlist tab as default (zero based)
		tabHost.setCurrentTab(0);
		
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
    
    public int getCurrentTab(){
    	return MyCha.currentTab;
    }
    
    public void setCurrentTab(int c){
    	MyCha.currentTab = c;
    }

}
