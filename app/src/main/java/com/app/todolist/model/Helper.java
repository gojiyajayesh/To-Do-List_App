 

package com.app.todolist.model;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.todolist.R;
import com.app.todolist.model.TodoTask.DeadlineColors;
import com.app.todolist.model.TodoTask.Priority;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Helper {

    public static final CharSequence DATE_FORMAT = "dd.MM.yyyy";

    public static String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(time));
        return DateFormat.format("dd.MM.yyyy", calendar).toString();
    }

    public static String getDateTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(time));
        return DateFormat.format("dd.MM.yyyy HH:mm", calendar).toString();
    }

    public static long getCurrentTimestamp() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static int getDeadlineColor(Context context, DeadlineColors color) {
        switch (color) {
            case RED:
                return ContextCompat.getColor(context, R.color.deadline_red);
            case BLUE:
                return ContextCompat.getColor(context, R.color.deadline_blue);
            case ORANGE:
                return ContextCompat.getColor(context, R.color.deadline_orange);
        }

        throw new IllegalArgumentException("Deadline color not defined.");
    }

    public static String priority2String(Context context, Priority prio) {

        switch (prio) {
            case HIGH:
                return context.getResources().getString(R.string.high_priority);
            case MEDIUM:
                return context.getResources().getString(R.string.medium_priority);
            case LOW:
                return context.getResources().getString(R.string.low_priority);
            default:
                return context.getResources().getString(R.string.unknown_priority);

        }
    }

    public static TextView getMenuHeader(Context context, String title) {
        TextView blueBackground = new TextView(context);
        blueBackground.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        blueBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        blueBackground.setText(title);
        blueBackground.setTextColor(ContextCompat.getColor(context, R.color.black));
        blueBackground.setPadding(65, 65, 65, 65);
        blueBackground.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        blueBackground.setTypeface(null, Typeface.BOLD);

        return blueBackground;
    }

}

