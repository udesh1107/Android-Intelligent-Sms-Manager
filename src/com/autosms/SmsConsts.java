package com.autosms;

/**
 * Contains SMS content provider constants. These values are copied from
 * com.android.provider.telephony.*
 */
public class SmsConsts {

    public static final String ID = "_id";

    public static final String BODY = "body";

    public static final String DATE = "date";

    public static final String THREAD_ID = "thread_id";

    public static final String TYPE = "type";

    public static final String READ = "read";
    
    public static final String STATUS = "status";
    
    public static final String SERVICE_CENTER = "service_center";

    public static final String PROTOCOL = "protocol";
    
    
    public static final int MESSAGE_TYPE_ALL = 0;

    public static final int MESSAGE_TYPE_INBOX = 1;

    public static final int MESSAGE_TYPE_SENT = 2;

    public static final int MESSAGE_TYPE_DRAFT = 3;

    public static final int MESSAGE_TYPE_OUTBOX = 4;

    public static final int MESSAGE_TYPE_FAILED = 5; // for failed outgoing
                                                     // messages

    public static final int MESSAGE_TYPE_QUEUED = 6; // for messages to send
                                                     // later
}
