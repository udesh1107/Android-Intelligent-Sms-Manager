package com.autosms;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditSMS extends Activity {
	

	private Button mBtnChoseDate;
	private Button mBtnChoseTime;
	private Button mBtnSelectContact;
	private TextView mDateText;
	private TextView mTimeText;
	private EditText mTxtPhoneNo;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHours;
	private int mMinutes;

	public static final String TAG = "TimedSMS.EditSMS";
	private static final int PICK_CONTACT = 3;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity 'EditSMS' State: onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editsms);

		
		mTimeText = (TextView) findViewById(R.id.txtTimeSend);
		mDateText = (TextView) findViewById(R.id.txtDateSend);
		mTxtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
		mBtnChoseDate = (Button) findViewById(R.id.btnChoseDate);
		mBtnChoseTime = (Button) findViewById(R.id.btnChoseTime);
		mBtnSelectContact = (Button) findViewById(R.id.btnSelectContact);

		
		mBtnSelectContact.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log
						.v(TAG,
								"Activity 'EditSMS' State: mBtnSelectContact.setOnClickListener()");
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});

		
		mBtnChoseDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log
						.v(TAG,
								"Activity 'EditSMS' State: mBtnChoseDate.setOnClickListener()");
				showDialog(DATE_DIALOG_ID);
			}
		});

		
		mBtnChoseTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log
						.v(TAG,
								"Activity 'EditSMS' State: mBtnChoseTime.setOnClickListener()");
				showDialog(TIME_DIALOG_ID);
			}
		});

		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHours = c.get(Calendar.HOUR_OF_DAY);
		mMinutes = c.get(Calendar.MINUTE);

		
		updateDateTimeText();

	}

	
	public void updateDateTimeText() {
		Log.v(TAG, "Activity 'EditSMS' State: updateDateText()");
		Date date = new Date(mYear, mMonth, mDay, mHours, mMinutes);
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.getDefault());
		mDateText.setText(dateFormat.format(date));
		DateFormat dateFormat1 = DateFormat.getTimeInstance(DateFormat.SHORT,
				Locale.getDefault());
		mTimeText.setText(dateFormat1.format(date));
	}

	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateTimeText();
		}
	};

	
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hour, int minute) {
			mHours = hour;
			mMinutes = minute;
			updateDateTimeText();
		}
	};

	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHours,
					mMinutes, android.text.format.DateFormat
							.is24HourFormat(getApplicationContext()));
		}
		return null;
	}

	
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				ContentResolver cr = getContentResolver();
				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					String name = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					String personId = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					Toast.makeText(getApplicationContext(), name,
							Toast.LENGTH_SHORT).show();
					mTxtPhoneNo.setText(name);

					Cursor phones = cr.query(Phone.CONTENT_URI, null,
							Phone.CONTACT_ID + " = " + personId, null, null);

					while (phones.moveToNext()) {
						String number = phones.getString(phones
								.getColumnIndex(Phone.NUMBER));
						int type = phones.getInt(phones
								.getColumnIndex(Phone.TYPE));
						switch (type) {
						case Phone.TYPE_HOME:
							Toast.makeText(getApplicationContext(),
									"Home No =" + number, Toast.LENGTH_SHORT)
									.show();
							break;
						case Phone.TYPE_MOBILE:
							Toast.makeText(getApplicationContext(),
									"Mobile No =" + number, Toast.LENGTH_SHORT)
									.show();
							break;
						case Phone.TYPE_WORK:

							break;
						}
					}
					phones.close();
				}
			}

		}
	}

}