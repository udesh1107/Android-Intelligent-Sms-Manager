package com.autosms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ParentalLogin extends Activity {
	private EditText password;

	public void onCreate(Bundle icis){
		super.onCreate(icis);
		setContentView(R.layout.sms_notes);
		
		
	}
	public void selfDestruct(View view) {
		
		
	    
	   password=(EditText)findViewById(R.id.widget30);
	    
	  String pswrds= password.getText().toString();
	     
	     if(pswrds.equalsIgnoreCase("admin")){
	    	 
	    	
	    	 Intent myintent=new Intent(view.getContext(), Words.class);
	    	 startActivityForResult(myintent, 0);
	     }
	     else{
		     Toast.makeText(this, pswrds+" is not CORRECT", Toast.LENGTH_SHORT).show(); 
	    finish();
	     }
	     
	 }
	
}
