package com.autosms;

final class Consts {
    
    /** TAG used for logging. */
    static final String TAG = "SmsSync";

    /** Gmail IMAP URI. */
    static final String IMAP_URI = "imap+ssl+://%s:%s@imap.gmail.com:993";
    
    /** Number of times a failed sync attempt should be retried when initiated by an alarm. */
    static final int NUM_AUTO_RETRIES = 2;
    
    /**
     * Key in the intent extras for indication whether all unsynced messages should
     * be skipped or not.
     */
    static final String KEY_SKIP_MESSAGES = "skip_messages";
    
    /**
     * Key in the intent extras for the number of retries when getting an exception
     * during sync.
     */
    static final String KEY_NUM_RETRIES = "num_retries";
    
    /** Website containing more information about this application. */
    static final String URL_INFO_LINK = "http://code.google.com/p/android-sms/wiki/UserGuide";

    /** Market link to details of this application. */
    static final String URL_MARKET_SEARCH =
        "http://market.android.com/search?q=pname:tv.studer.smssync";
    
}
