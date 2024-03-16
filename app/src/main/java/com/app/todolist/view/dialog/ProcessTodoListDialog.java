 

package com.app.todolist.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.todolist.R;
import com.app.todolist.model.TodoList;



public class ProcessTodoListDialog extends FullScreenDialog {

    private ProcessTodoListDialog self = this;
    private Button buttonOkay;
    private Button buttonCancel;
    private EditText etName, etDescription;
    private TodoList todoList;


    public ProcessTodoListDialog(Context context) {
        super(context, R.layout.add_todolist_dialog);

        initGui();

        todoList = new TodoList();
        todoList.setCreated();
        //todoList.setDbState(DBQueryHandler.ObjectStates.INSERT_TO_DB);
    }

    public ProcessTodoListDialog(Context context, TodoList list2Change) {
        super(context, R.layout.add_todolist_dialog);

        initGui();
        etName.setText(list2Change.getName());
        todoList = list2Change;
        todoList.setChanged();
        //todoList.setDbState(DBQueryHandler.ObjectStates.UPDATE_DB);
    }



    private void initGui() {
        buttonOkay = (Button) findViewById(R.id.bt_newtodolist_ok);
        buttonOkay.setOnClickListener(new OkayButtonListener());
        buttonCancel = (Button) findViewById(R.id.bt_newtodolist_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        etName = (EditText) findViewById(R.id.et_todo_list_name);
    }

    private class OkayButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // prepare list data
            String listName = etName.getText().toString();

            if (listName.equals("") || listName == null) {
                Toast.makeText(getContext(), getContext().getString(R.string.list_name_must_not_be_empty), Toast.LENGTH_SHORT).show();
            } else {

                if (changesMade(listName)) {
                    todoList.setName(listName);
                    callback.finish(todoList);
                }
                self.dismiss();
            }
        }
    }

    private boolean changesMade(String listName) {

        // check if real changes were made
        if (listName.equals(todoList.getName())) {
            return false;
        }

        return true;
    }


}
