 

package com.app.todolist.view.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.todolist.R;
import com.app.todolist.model.TodoList;
import com.app.todolist.model.TodoTask;
import com.app.todolist.model.database.DBQueryHandler;
import com.app.todolist.model.database.DatabaseHelper;

import java.util.ArrayList;

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<TodoList> lists;
    private Context mContext;
    private static final int ID_CONSTANT = 0x0101010;
    private ArrayList<TodoTask> listTasks;
    private String listChosen;
    private static Context c;
    private static int id;



    public WidgetViewsFactory(Context context, Intent intent){
        mContext = context;
        lists = new ArrayList<TodoList>();
        listTasks = new ArrayList<TodoTask>();
    }


    @Override
    public void onCreate() {
        listChosen = getListName(c, id);
            lists = DBQueryHandler.getAllToDoLists(DatabaseHelper.getInstance(mContext).getReadableDatabase());
            for (int i=0; i < lists.size(); i++){
                if (lists.get(i).getName().equals(this.listChosen))
                    listTasks = lists.get(i).getTasks();

            }


    }

    @Override
    public int getCount() {
        return listTasks.size();
    }



    @Override
    public void onDataSetChanged() {
        listChosen = getListName(c, id);
            lists = DBQueryHandler.getAllToDoLists(DatabaseHelper.getInstance(mContext).getReadableDatabase());
            for (int i=0; i < lists.size(); i++){
                if (lists.get(i).getName().equals(this.listChosen))
                    listTasks = lists.get(i).getTasks();
            }
    }



    @Override
    public int getViewTypeCount() {
        return 1;
    }



    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION){
            return null;
        }

        TodoTask todo = listTasks.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_tasks);
        if (todo.getDone()){
            itemView.setViewVisibility(R.id.widget_done, View.VISIBLE);
            itemView.setViewVisibility(R.id.widget_undone, View.INVISIBLE);
        } else if (!todo.getDone()) {
            itemView.setViewVisibility(R.id.widget_done, View.INVISIBLE);
            itemView.setViewVisibility(R.id.widget_undone, View.VISIBLE);
        }

        itemView.setTextViewText(R.id.tv_widget_task_name, todo.getName());
        itemView.setEmptyView(R.id.tv_empty_widget, R.string.empty_todo_list);

        Intent fillInIntent = new Intent();
        itemView.setOnClickFillInIntent(R.id.tv_widget_task_name, fillInIntent);
        itemView.setOnClickFillInIntent(R.id.widget_undone, fillInIntent);
        itemView.setOnClickFillInIntent(R.id.widget_done, fillInIntent);




        return itemView;
    }



    @Override
    public long getItemId(int position) {

        return ID_CONSTANT + position;
    }



    @Override
    public void onDestroy() {
        lists.clear();
    }



    @Override
    public RemoteViews getLoadingView() {
        return null;
    }



    @Override
    public boolean hasStableIds() {
        return true;
    }



    public static String getListName(Context context, int AppWidgetId) {
        c = context;
        id = AppWidgetId;
        return TodoListWidgetConfigureActivity.loadTitlePref(context, AppWidgetId);
    }


}




