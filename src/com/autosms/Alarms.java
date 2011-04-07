package com.autosms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarms {
    /**
     * Schedule a sync right after an SMS arrived.
     */
    static void scheduleIncomingSync(Context ctx) {
        scheduleSync(ctx, PrefStore.getIncomingTimeoutSecs(ctx));
    }
    
    /**
     * Schedule a sync at default rate for syncing outgoing SMS.
     */
    static void scheduleRegularSync(Context ctx) {
        scheduleSync(ctx, PrefStore.getRegularTimeoutSecs(ctx));
    }
    
    static void cancel(Context ctx) {
        AlarmManager aMgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        aMgr.cancel(createPendingIntent(ctx));
    }
    
    private static void scheduleSync(Context ctx, int inSeconds) {
        if (!PrefStore.isEnableAutoSync(ctx)) {
            Log.d(Consts.TAG, "Not scheduling sync because auto sync is disabled.");
            return;
        }
        
        long atTime = System.currentTimeMillis() + inSeconds * 1000l;
        PendingIntent pi = createPendingIntent(ctx);
        AlarmManager aMgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        aMgr.set(AlarmManager.RTC_WAKEUP, atTime, pi);
        Log.d(Consts.TAG, "Scheduled sync due in " + inSeconds + " seconds.");
    }
    
    private static PendingIntent createPendingIntent(Context ctx) {
        Intent serviceIntent = new Intent(ctx, SmsSyncService.class);
        serviceIntent.putExtra(Consts.KEY_NUM_RETRIES, Consts.NUM_AUTO_RETRIES);
        return PendingIntent.getService(ctx, 0, serviceIntent, 0);
    }
    
}

