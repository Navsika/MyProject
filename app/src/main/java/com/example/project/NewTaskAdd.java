package com.example.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.project.Model.ModelOfToDo;
import com.example.project.Utils.OpenHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewTaskAdd extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText, newDescriptionText;
    private Button newTaskSaveButton;
    private RadioGroup groupOfStages, groupOfStatus;
    private RadioButton stage1, stage2, stage3, status1, status2, status3;

    private OpenHelper db;

    public static NewTaskAdd newInstance(){
        return new NewTaskAdd();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        newDescriptionText = getView().findViewById(R.id.newDescriptionTask);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
        groupOfStages = getView().findViewById(R.id.group_stages);
        groupOfStatus= getView().findViewById(R.id.group_status);
        stage1 = getView().findViewById(R.id.stage_radio_1);
        stage2 = getView().findViewById(R.id.stage_radio_2);
        stage3 = getView().findViewById(R.id.stage_radio_3);
        status1 = getView().findViewById(R.id.status_radio_1);
        status2 = getView().findViewById(R.id.status_radio_2);
        status3 = getView().findViewById(R.id.status_radio_3);


        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            String description = bundle.getString("description");
            newDescriptionText.setText(description);
            String stage = bundle.getString("stage");
            switch(stage){
                case "To Do":
                    stage1.setChecked(true);
                    break;
                case "Is Doing":
                    stage2.setChecked(true);
                    break;
                case "Done":
                    stage3.setChecked(true);
                    break;

            }

            Integer status = bundle.getInt("status");
            switch(status){
                case 1:
                    status1.setChecked(true);
                    break;
                case 2:
                    status2.setChecked(true);
                    break;
                case 3:
                    status3.setChecked(true);
                    break;

            }
            assert task != null;
            //if(task.length()>0)
            //    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        db = new OpenHelper(getActivity());
        db.openDatabase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((s.toString().equals(""))&&(groupOfStages.getCheckedRadioButtonId()!=-1)&&(groupOfStatus.getCheckedRadioButtonId()!=-1)){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                String description = newDescriptionText.getText().toString();
                String stage ="";
                int checkId = groupOfStages.getCheckedRadioButtonId();
                switch (checkId){
                    case R.id.stage_radio_1:
                         stage = "To Do";
                        break;
                    case R.id.stage_radio_2:
                         stage = "Is Doing";
                        break;
                    case R.id.stage_radio_3:
                         stage = "Done";
                        break;

                }
                int status = 0;
                int checkId1 = groupOfStatus.getCheckedRadioButtonId();
                switch (checkId1){
                    case R.id.status_radio_1:
                        status = 1;
                        break;
                    case R.id.status_radio_2:
                        status = 2;
                        break;
                    case R.id.status_radio_3:
                        status = 3;
                        break;

                }




                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                    db.updateDescription(bundle.getInt("id"),description);
                    db.UpdateStatus(bundle.getInt("id"),status);
                    db.updateStage(bundle.getInt("id"),stage);
                }
                else {
                    ModelOfToDo task = new ModelOfToDo();
                    task.setTask(text);
                    task.setDescription(description);
                    task.setStage(stage);
                    task.setDone(0);
                    task.setStatus(status);

                    db.insertTask(task);
                }
                dismiss();
            }
        });

    }




    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }




}
