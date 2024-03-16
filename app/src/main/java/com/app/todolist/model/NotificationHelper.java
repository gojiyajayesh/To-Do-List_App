
package com.app.todolist.model;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.todolist.view.MainActivity;
import com.app.todolist.view.TodoTasksFragment;

import com.app.todolist.R;

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNEL_ID = "my_channel_01";
    public static final CharSequence name = "Channel";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

        channel.setDescription("Test");
        channel.enableLights(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


    public NotificationCompat.Builder getNotification(String title, String message, TodoTask task) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setAutoCancel(true)
                .setLights(ContextCompat.getColor(this, R.color.colorPrimary), 1000, 500);
        ;
        if (task.hasDeadline())
            builder.setContentText(message);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notify", true)) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(uri);
        }

        Intent snooze = new Intent(this, MainActivity.class);
        PendingIntent pendingSnooze =
                PendingIntent.getActivity(
                        this,
                        0,
                        snooze,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        snooze.putExtra("snooze", 900000);
        snooze.putExtra("taskId", task.getId());
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(MainActivity.KEY_SELECTED_FRAGMENT_BY_NOTIFICATION, TodoTasksFragment.KEY);
        resultIntent.putExtra(TodoTask.PARCELABLE_KEY, task);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addNextIntent(snooze);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.addAction(R.drawable.snooze, "Snooze", pendingSnooze);
        builder.addAction(R.drawable.done, "Set done", resultPendingIntent);
        builder.setContentIntent(resultPendingIntent);

        return builder;
    }


}
