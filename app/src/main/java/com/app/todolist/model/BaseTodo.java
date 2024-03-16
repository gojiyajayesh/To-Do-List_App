 

package com.app.todolist.model;

import com.app.todolist.model.database.DBQueryHandler;

public abstract class BaseTodo {

    protected int id;

    protected int progress;

    public int getProgress(){
        return progress;
    }

    protected String name, description;
    protected DBQueryHandler.ObjectStates dbState;

    public BaseTodo() {
        dbState = DBQueryHandler.ObjectStates.NO_DB_ACTION;
    }

    public DBQueryHandler.ObjectStates getDBState() {
        return dbState;
    }

    public void setCreated() {
        this.dbState = DBQueryHandler.ObjectStates.INSERT_TO_DB;
    }

    public void setChanged() {
        if(this.dbState == DBQueryHandler.ObjectStates.NO_DB_ACTION)
            this.dbState = DBQueryHandler.ObjectStates.UPDATE_DB;
    }

    public void setChangedFromPomodoro() {
            this.dbState = DBQueryHandler.ObjectStates.UPDATE_FROM_POMODORO;
    }



    public void setUnchanged() {
        this.dbState = DBQueryHandler.ObjectStates.NO_DB_ACTION;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
