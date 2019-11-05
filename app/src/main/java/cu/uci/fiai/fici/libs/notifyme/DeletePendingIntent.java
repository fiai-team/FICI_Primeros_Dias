package cu.uci.fiai.fici.libs.notifyme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import static cu.uci.fiai.fici.libs.notifyme.Notification.NotificationEntry.TABLE_NAME;

public class DeletePendingIntent extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationId = intent.getStringExtra("_id");
        cu.uci.fiai.fici.libs.notifyme.Notification.NotificationDBHelper mDbHelper = new cu.uci.fiai.fici.libs.notifyme.Notification.NotificationDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, cu.uci.fiai.fici.libs.notifyme.Notification.NotificationEntry._ID+" = "+notificationId,null);
        db.close();
    }

}
