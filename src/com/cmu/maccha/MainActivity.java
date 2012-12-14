package com.cmu.maccha;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	static final String USER = "jiasiz@andrew.cmu.edu";//"seethaa@cmu.edu";
	static final String PASS = "111111";//"chapwd";
	private static String user;
	private static String pass;
	static final String TAG = "MACCHA";
	private static EditText un;
	private static EditText pw;
	public static String localURL = "128.237.217.111";//getResources().getString(R.string.localURL);
	public static String userNum = "1"; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
    	
        un = (EditText) this.findViewById(R.id.username);
        Editable usrnm = un.getText();
        user = usrnm.toString();
        
        pw = (EditText) this.findViewById(R.id.password);
        Editable pwd = pw.getText();
        pass = pwd.toString();
        
        Log.d("TAG", "user: " + user);
        Button submitButton = (Button) this.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submit);   
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    private OnClickListener submit = new OnClickListener(){
    	@Override
    	public void onClick(View v) {
    		//check if user is real..sign in user:
    		
    		Editable usrnm = un.getText();
            user = usrnm.toString();
         
            Editable pwd = pw.getText();
            pass = pwd.toString();
            
    		Log.d("TAG", "USER PRESSED SUBMIT: " + user + ", " + pass);
//    		if (user.equals(USER) && pass.equals(PASS)){
//    			 Log.d("TAG", "User authenticated..");
	        	Intent intent = new Intent(MainActivity.this, MyCha.class); 
	        	startActivity(intent);
//    		}
        }
    };
    
    public String getURL(){
    	return this.localURL;
    }

}
