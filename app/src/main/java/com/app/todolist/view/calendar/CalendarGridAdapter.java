package com.app.todolist.view.calendar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.app.todolist.R;
import com.app.todolist.model.Helper;
import com.app.todolist.model.TodoTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class CalendarGridAdapter extends ArrayAdapter<Date> {

    private ArrayList<TodoTask> todoTasks;
    private final LayoutInflater inflater;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d");
    private ColorStateList oldColors;
    private int currentMonth;
    private HashMap<String, ArrayList<TodoTask>> tasksPerDay = new HashMap<>();

    public CalendarGridAdapter(Context context, int resource) {
        super(context, resource, new ArrayList<Date>());

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        CalendarDayViewHolder dayViewHolder;

        Date dateAtPos = getItem(position);

        Date todayDate = new Date();
        Calendar todayCal = Calendar.getInstance();
        Calendar posCal = Calendar.getInstance();
        todayCal.setTime(todayDate);
        posCal.setTime(dateAtPos);

        if (view == null) {
            dayViewHolder = new CalendarDayViewHolder();

            view = inflater.inflate(R.layout.calendar_day, parent, false);
            dayViewHolder.dayText = view.findViewById(R.id.tv_calendar_day_content);
            oldColors = dayViewHolder.dayText.getTextColors();

            view.setTag(dayViewHolder);

        } else {
            dayViewHolder = (CalendarDayViewHolder) view.getTag();
        }

        // grey day out if it is outside the current month
        if (posCal.get(Calendar.MONTH) != currentMonth) {
            dayViewHolder.dayText.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        } else if (sameDay(posCal, todayCal)) {
            dayViewHolder.dayText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else {
            dayViewHolder.dayText.setTextColor(oldColors);
        }

        // add color bar if a task has its deadline on this day
        String day = DateFormat.format(Helper.DATE_FORMAT, dateAtPos).toString();
        ArrayList<TodoTask> tasksToday = tasksPerDay.get(day);
        if (tasksToday != null) {
            Drawable border = ContextCompat.getDrawable(getContext(), R.drawable.border_green);
            for (TodoTask t : tasksToday) {
                if (!t.getDone())
                    border = ContextCompat.getDrawable(getContext(), R.drawable.border_blue);
            }
            dayViewHolder.dayText.setBackground(border);
        } else {
            dayViewHolder.dayText.setBackgroundResource(0);
        }

        dayViewHolder.dayText.setText(dateToStr(posCal));
        return view;
    }

    private boolean sameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private String dateToStr(Calendar c) {
        return dateFormat.format(c.getTime());
    }


    public void setMonth(int month) {
        this.currentMonth = month;
    }

    public void setTodoTasks(HashMap<String, ArrayList<TodoTask>> tasksPerDay) {
        this.tasksPerDay = tasksPerDay;
    }

    private class CalendarDayViewHolder {
        public TextView dayText;
    }
}
