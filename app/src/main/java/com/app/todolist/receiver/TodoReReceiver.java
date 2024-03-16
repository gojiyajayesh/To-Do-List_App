package com.app.todolist.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.todolist.view.MainActivity;

public class TodoReReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("todo_id", -1);
        String name = intent.getStringExtra("todo_name");
        int progress = intent.getIntExtra("todo_progress", -1);

        if (id == -1 || name == null) {
            Log.e("MainActivity", "Todo Intent is not complete");
            return;
        }


        Intent runIntent = new Intent(context, MainActivity.class);
        runIntent.putExtra(MainActivity.COMMAND, MainActivity.COMMAND_UPDATE).putExtra("todo_id", id).putExtra("todo_name", name).putExtra("todo_progress", progress).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(runIntent);

    }
}
