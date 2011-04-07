package com.autosms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Words extends Activity {
	private EditText words;

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.words);
		 
			}
	
	public void selfWords(View view){
		words=(EditText)findViewById(R.id.widget32);
	String word1=words.getText().toString();
	DataWords db=new DataWords(this);
	db.open();
	db.insertTitle(word1);
	db.close();
	}


}
