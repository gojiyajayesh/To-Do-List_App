package com.app.todolist.view.calendar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.app.todolist.R;
import com.app.todolist.model.TodoTask;
import com.app.todolist.model.database.DatabaseHelper;
import com.app.todolist.view.ExpandableTodoTaskAdapter;

import java.util.ArrayList;


public class CalendarPopup extends AppCompatActivity {

    RelativeLayout rl;
    private DatabaseHelper dbhelper;
    private ExpandableListView lv;
    private ExpandableTodoTaskAdapter expandableTodoTaskAdapter;
    private ArrayList<TodoTask> tasks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendar_popup);

        rl = findViewById(R.id.relative_deadline);
        lv = findViewById(R.id.deadline_tasks);

        Toolbar toolbar = findViewById(R.id.toolbar_deadlineTasks);

        if (toolbar != null) {
            toolbar.setTitle(R.string.deadline);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        Bundle b = getIntent().getExtras();
        if (b != null) tasks = b.getParcelableArrayList("Deadlines");
        updateAdapter();


    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateAdapter() {

        expandableTodoTaskAdapter = new ExpandableTodoTaskAdapter(this, tasks);
        lv.setAdapter(expandableTodoTaskAdapter);

    }


}




