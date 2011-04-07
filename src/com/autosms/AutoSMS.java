package com.autosms;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AutoSMS extends ListActivity {
//main menu.
	 //Safe to hold on to this.
    // private Menu mMenu;
    
    public static final String TAG = "TimedSM";


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	Log.v(TAG, "Activity 'AutoSMS' State: onCreate()");
    	super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MAIN_NEW_TASK));
        
        getListView().setTextFilterEnabled(true);
       
    }
    
    // Create the Options Menu for AutoSMS    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hold on to this
    	Log.v(TAG, "Activity 'AutoSMS' State: onCreateOptionsMenu()");
        //mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    //check the Options Menu events for Items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Log.v(TAG, "Activity 'AutoSMS' State: onOptionsItemSelected()");
        switch (item.getItemId()) {
            // For "Title only": Examples of matching an ID with one assigned in
            //                   the XML
            case R.id.add_new_sms:
            	Log.v(TAG, "Option Menu item with id 'add_new_sms' clicked!");
            	Intent iEditSMS_new = new Intent(this, EditSMS.class);
                startActivity(iEditSMS_new);
                
                return true;

            case R.id.settings:
            	Log.v(TAG, "Option Menu item with id 'settings' clicked!");
                Toast.makeText(this, "Settings click!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sms_notes:
            	Intent ismsNotes = new Intent(this, ParentalLogin.class);
               startActivity(ismsNotes);
                return true;
            case R.id.sms_backup:
            	//Toast.makeText(this, "SmsSync", Toast.LENGTH_SHORT).show();
            	Intent ismsBackup= new Intent(this,backupMain.class);
            	startActivity(ismsBackup);
            	break;
            	
			 
            // Generic catch all for all the other menu resources
            default:
                // Don't toast text when a submenu is clicked
                if (!item.hasSubMenu()) {
                    Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }

        return false;
    }

    
    static final String[] MAIN_NEW_TASK = new String[] {
    	"New Scheduled SMS","Parental Control","SMS Backup"
    };
    
}
