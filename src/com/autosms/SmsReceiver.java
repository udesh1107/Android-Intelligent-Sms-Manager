package com.autosms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle; //import android.telephony.gsm.SmsMessage;
import android.telephony.SmsMessage;
import android.telephony.gsm.SmsManager;
import android.util.Log;

@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver {
	Vector<String> words = new Vector<String>();

	//------------bundle the sms data in to a readable message---------------

	private SmsMessage[] getMessagesFromIntent(Intent intent) {
		SmsMessage retMsgs[] = null;
		Bundle bdl = intent.getExtras();
		try {
			Object pdus[] = (Object[]) bdl.get("pdus");
			retMsgs = new SmsMessage[pdus.length];
			for (int n = 0; n < pdus.length; n++) {
				byte[] byteData = (byte[]) pdus[n];
				retMsgs[n] = SmsMessage.createFromPdu(byteData);
			}

		} catch (Exception e) {
			Log.e("GetMessages", "fail", e);
		}
		return retMsgs;
	}
//------------called when a sms is received---------------------------
	public void onReceive(Context context, Intent intent) {

		if (!intent.getAction().equals(
				"android.provider.Telephony.SMS_RECEIVED")) {
			return;
		}
		SmsMessage msg[] = getMessagesFromIntent(intent);

		SmsManager.getDefault().sendTextMessage("5556", null, " received ",
				null, null);

		
		String message = msg[0].getMessageBody();
		StringTokenizer st = new StringTokenizer(message);

		while (st.hasMoreElements()) {
			words.add(st.nextToken());
		}
		
		if (message != null && message.length() > 0) {
			Log.i("MessageListener:", message);
			//-----Search the database-----------------
			
			
			DataWords db = new DataWords(context);
	       db.badWords();
			
			
			db.open();
			
			
			Cursor c = db.getAllTitles();
			if (c.moveToFirst()) {
				do {

					for (int k = 0; words.size() > k; k++) {
       //---------message sending
						if (c.getString(1).toLowerCase().contains(
								words.elementAt(k).toLowerCase())) {
							String sender = msg[0].getOriginatingAddress();
							String msgBody = msg[0].getMessageBody();
						

							Calendar cal = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy.MM.dd G 'at' hh:mm:ss z");
							String currentDate = sdf.format(cal.getTime());
							SmsManager.getDefault().sendTextMessage(
									"5556",
									null,
									" The sender with mobile number " + sender
											+ " sent this " + msgBody + " on "
											+ currentDate, null, null);
							
							abortBroadcast();
							break;
						}

					}



				} while (c.moveToNext());
			}
			db.close();
					}
	}
}
