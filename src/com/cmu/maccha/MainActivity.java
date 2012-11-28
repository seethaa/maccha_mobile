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
	static final String USER = "seethaa@cmu.edu";
	static final String PASS = "chapwd";
	private static String user;
	private static String pass;
	static final String TAG = "MACCHA";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        EditText un = (EditText) this.findViewById(R.id.username);
        Editable usrnm = un.getText();
        user = usrnm.toString();
        
        EditText pw = (EditText) this.findViewById(R.id.password);
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
    		Log.d("TAG", "USER PRESSED SUBMIT");
    		//if (user.equals(USER) && pass.equals(PASS)){
	        	Intent intent = new Intent(MainActivity.this, MyCha.class); 
	        	startActivity(intent);
    		//}
        }
    };

}
