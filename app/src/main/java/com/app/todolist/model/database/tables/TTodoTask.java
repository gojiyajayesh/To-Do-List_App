 

package com.app.todolist.model.database.tables;

public class TTodoTask {

    // TAG
    private static final String TAG = TTodoList.class.getSimpleName();

    // columns + tablename
    public static final String TABLE_NAME = "todo_task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_LIST_ID = "todo_list_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_DONE = "done";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_NUM_SUBTAKS = "num_subtasks";
    public static final String COLUMN_DEADLINE_WARNING_TIME = "deadline_warning_time"; // absolut value in seconds
    public static final String COLUMN_LIST_POSITION = "position_in_todo_list";
    public static final String COLUMN_TRASH = "in_trash";


    // sql table creation
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TODO_LIST_ID + " INTEGER NOT NULL, " +
            COLUMN_LIST_POSITION + " INTEGER NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            COLUMN_PRIORITY + " INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_DEADLINE + " DATETIME DEFAULT NULL, " +
            COLUMN_DONE + " INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_PROGRESS + " INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_NUM_SUBTAKS + "INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_DEADLINE_WARNING_TIME + " NUMERIC NULL DEFAULT NULL, " +
            COLUMN_TRASH + " INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (" + COLUMN_TODO_LIST_ID + ") REFERENCES " + TTodoList.TABLE_NAME + "(" + TTodoList.COLUMN_ID + "));";
}
