package com.autosms;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class backupMain extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backupmain);
        Button buttonStart = (Button)findViewById(R.id.startalarm);
        buttonStart.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View arg0) {
        		//Toast.makeText(Main.this, "Start Alarm", Toast.LENGTH_LONG).show();
        		Intent intent=new Intent(arg0.getContext(), SmsSync.class);
        		startActivity(intent);
        		
        	}
        });
	}
}
