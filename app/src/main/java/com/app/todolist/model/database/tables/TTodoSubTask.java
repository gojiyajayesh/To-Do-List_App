 

package com.app.todolist.model.database.tables;

public final class TTodoSubTask {

    // TAG
    private static final String TAG = TTodoList.class.getSimpleName();

    // columns + tablename
    public static final String TABLE_NAME = "todo_subtask";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_ID = "todo_task_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DONE = "done";
    public static final String COLUMN_TRASH = "in_trash";

    // sql table creation
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TASK_ID + " INTEGER NOT NULL, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_DONE + " INTEGER, " +
            COLUMN_TRASH + " INTEGER NOT NULL DEFAULT 0, FOREIGN KEY (" + COLUMN_TASK_ID + ") REFERENCES " + TTodoTask.TABLE_NAME + "(" + TTodoTask.COLUMN_ID + "));";

}
//  , " + COLUMN_TRASH + " INTEGER NOT NULL DEFAULT 0