 

package com.app.todolist.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.todolist.R;
import com.app.todolist.model.TodoSubTask;



public class ProcessTodoSubTaskDialog extends FullScreenDialog {

    private EditText etSubtaskName;
    private Button cancelButton;
    private TodoSubTask subtask;
    private TextView dialogTitleNew;
    private TextView dialogTitleEdit;

    public ProcessTodoSubTaskDialog(Context context) {
        super(context, R.layout.add_subtask_dialog);

        initGui();
        this.subtask = new TodoSubTask();
        this.subtask.setCreated();
        //this.subtask.setDbState(DBQueryHandler.ObjectStates.INSERT_TO_DB);
    }

    public ProcessTodoSubTaskDialog(Context context, TodoSubTask subTask) {
        super(context, R.layout.add_subtask_dialog);

        initGui();
        this.subtask = subTask;
        this.subtask.setChanged();
        //this.subtask.setDbState(DBQueryHandler.ObjectStates.UPDATE_DB);

        etSubtaskName.setText(subTask.getName());
    }

    private void initGui() {
        etSubtaskName = (EditText) findViewById(R.id.et_new_subtask_name);
        Button okButton = (Button) findViewById(R.id.bt_new_subtask_ok);
        cancelButton = (Button) findViewById(R.id.bt_new_subtask_cancel);

        //initialize titles of the dialog
        dialogTitleEdit = (TextView) findViewById(R.id.dialog_edit_sub);
        dialogTitleNew = (TextView) findViewById(R.id.dialog_subtitle);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etSubtaskName.getText().toString();

                if(name.equals("")) {
                    Toast.makeText(getContext(), getContext().getString(R.string.todo_name_must_not_be_empty), Toast.LENGTH_SHORT).show();
                } else {

                    subtask.setName(name);
                    callback.finish(subtask);
                    ProcessTodoSubTaskDialog.this.dismiss();
                }
            }
        });

        Button cancelButton = (Button) findViewById(R.id.bt_new_subtask_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProcessTodoSubTaskDialog.this.dismiss();
            }
        });
    }

    public void titleEdit() {
        dialogTitleNew.setVisibility(View.GONE);
        dialogTitleEdit.setVisibility(View.VISIBLE);
    }
}
