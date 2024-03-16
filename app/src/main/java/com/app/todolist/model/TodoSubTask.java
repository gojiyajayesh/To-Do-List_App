 

package com.app.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoSubTask extends BaseTodo implements Parcelable {

    private String name;
    private boolean done;
    private boolean inTrash;
    private long taskIdForeignKey;

    private int progress = -1;

    @Override
    public int getProgress() {
        return progress;
    }

    public TodoSubTask() {
        super();
        done = false;
        inTrash = false;
    }

    public TodoSubTask(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        done = parcel.readByte() != 0;
        inTrash = parcel.readByte() != 0;
        taskIdForeignKey = parcel.readLong();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean getDone() {
        return done;
    }

    public boolean isInTrash() {return inTrash; }

    public void setDone(boolean d) {
        done = d;
    }

    public void setInTrash(boolean a) { inTrash = a; }

    public static final Parcelable.Creator<TodoSubTask> CREATOR =
            new Creator<TodoSubTask>() {
                @Override
                public TodoSubTask createFromParcel(Parcel source) {
                    return new TodoSubTask(source);
                }

                @Override
                public TodoSubTask[] newArray(int size) {
                    return new TodoSubTask[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (done ? 1 : 0));
        dest.writeByte((byte) (inTrash ? 1 : 0));
        dest.writeLong(taskIdForeignKey);
    }

    public void setTaskId(long taskIdForeignKey) {
        this.taskIdForeignKey = taskIdForeignKey;
    }

    public long getTaskId() {
        return taskIdForeignKey;
    }


    public boolean checkQueryMatch(String query)
    {
        // no query? always match!
        if(query == null || query.isEmpty())
            return true;

        String queryLowerCase = query.toLowerCase();
        if(this.name.toLowerCase().contains(queryLowerCase))
            return true;
        return false;
    }
}
