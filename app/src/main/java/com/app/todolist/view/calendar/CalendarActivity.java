package com.app.todolist.view.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.app.todolist.R;
import com.app.todolist.model.Helper;
import com.app.todolist.model.TodoList;
import com.app.todolist.model.TodoTask;
import com.app.todolist.model.database.DBQueryHandler;
import com.app.todolist.model.database.DatabaseHelper;
import com.app.todolist.view.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class CalendarActivity extends AppCompatActivity {

    protected MainActivity containerActivity;
    private CalendarView calendarView;
    private CalendarGridAdapter calendarGridAdapter;
    private final HashMap<String, ArrayList<TodoTask>> tasksPerDay = new HashMap<>();
    private DatabaseHelper dbHelper;
    private ArrayList<TodoTask> todaysTasks;

  /*  private ExpandableListView expandableListView;
    private ExpandableTodoTaskAdapter taskAdapter; */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar_calendar);

        if (toolbar != null) {
            toolbar.setTitle(R.string.calendar);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        calendarView = findViewById(R.id.calendar_view);
        calendarGridAdapter = new CalendarGridAdapter(this, R.layout.calendar_day);
        calendarView.setGridAdapter(calendarGridAdapter);
        //expandableListView = (ExpandableListView) findViewById(R.id.exlv_tasks);

        dbHelper = DatabaseHelper.getInstance(this);
        todaysTasks = new ArrayList<>();

        updateDeadlines();

        calendarView.setNextMonthOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.incMonth(1);
                calendarView.refresh();
            }
        });

        calendarView.setPrevMontOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.incMonth(-1);
                calendarView.refresh();
            }
        });

        calendarView.setDayOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todaysTasks.clear();
                updateDeadlines();
                Date selectedDate = calendarGridAdapter.getItem(position);
                String key = absSecondsToDate(selectedDate.getTime() / 1000);
                todaysTasks = tasksPerDay.get(key);
                if (todaysTasks == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_deadline_today), Toast.LENGTH_SHORT).show();
                } else {
                    showDeadlineTasks(todaysTasks);
                }
            }
        });

    }


    private void updateDeadlines() {
        ArrayList<TodoList> todoLists = DBQueryHandler.getAllToDoLists(dbHelper.getReadableDatabase());
        ArrayList<TodoTask> todoTasks = DBQueryHandler.getAllToDoTasks(dbHelper.getReadableDatabase());
        tasksPerDay.clear();
        //for (TodoList list : todoLists){
        for (TodoTask task : todoTasks) {
            long deadline = task.getDeadline();
            String key = absSecondsToDate(deadline);
            if (!tasksPerDay.containsKey(key)) {
                tasksPerDay.put(key, new ArrayList<TodoTask>());
            }
            tasksPerDay.get(key).add(task);
            //}
        }
        calendarGridAdapter.setTodoTasks(tasksPerDay);
        calendarGridAdapter.notifyDataSetChanged();
        //containerActivity.getSupportActionBar().setTitle(R.string.calendar);
    }

    private String absSecondsToDate(long seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(TimeUnit.SECONDS.toMillis(seconds));
        return DateFormat.format(Helper.DATE_FORMAT, cal).toString();
    }

    private void showDeadlineTasks(ArrayList<TodoTask> tasks) {
        Intent intent = new Intent(this, CalendarPopup.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList("Deadlines", tasks);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
