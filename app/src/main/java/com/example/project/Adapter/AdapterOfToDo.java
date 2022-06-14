package com.example.project.Adapter;

import static com.example.project.TimerActivity.DESCRIPTION_TEXT;
import static com.example.project.TimerActivity.TASK_TEXT;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.MainActivity;
import com.example.project.Model.ModelOfToDo;
import com.example.project.NewTaskAdd;
import com.example.project.R;
import com.example.project.TimerActivity;
import com.example.project.Utils.OpenHelper;

import java.util.List;

public class AdapterOfToDo extends RecyclerView.Adapter<AdapterOfToDo.ViewHolder> {

    public static int coin = 0;

    private List<ModelOfToDo> toDoList;
    private OpenHelper db;
    private MainActivity activity;
    private Context mContext;
    private static String r ="";
    private RecyclerView recyclerView;

    public AdapterOfToDo(OpenHelper db, MainActivity activity, Context context, RecyclerView recyclerView){
        this.db = db;
        this.activity = activity;
        this.mContext = context;
        this.recyclerView = recyclerView;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.layout_of_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        db.openDatabase();

        ColorStateList checkboxColorRed = ContextCompat.getColorStateList(getContext(), R.color.checkbox_theme_red);
        ColorStateList checkboxColorYellow = ContextCompat.getColorStateList(getContext(), R.color.checkbox_theme_yellow);
        ColorStateList checkboxColorGreen = ContextCompat.getColorStateList(getContext(), R.color.checkbox_theme_green);
        ColorStateList checkboxColorOrange = ContextCompat.getColorStateList(getContext(), R.color.checkbox_theme_orange);

        final ModelOfToDo item = toDoList.get(position);
        holder.textOfTask.setText(item.getTask());
        holder.textOfDescription.setText(item.getDescription());
        holder.textOfStage.setText(item.getStage());
        switch(item.getStatus()){
            case 0:
                holder.checkBox.setButtonTintList(checkboxColorOrange);
                break;
            case 1:
                holder.checkBox.setButtonTintList(checkboxColorRed);
                break;
            case 2:
                holder.checkBox.setButtonTintList(checkboxColorYellow);
                break;
            case 3:
                holder.checkBox.setButtonTintList(checkboxColorGreen);
                break;

        }
        holder.checkBox.setChecked(toBoolean(item.getDone()));
        if (item.getStage().equals("Done")){
            holder.checkBox.setChecked(true);
            db.UpdateDone(item.getId(), 1);

        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.UpdateDone(item.getId(), 1);
                    r = item.getStage();
                    db.updateStage(item.getId(), "Done");

                } else {
                    db.UpdateDone(item.getId(), 0);
                    db.updateStage(item.getId(), r);


                }
            }
        });


        holder.startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TimerActivity.class);
                intent.putExtra(TASK_TEXT, item.getTask());
              //  intent.putExtra(DESCRIPTION_TEXT, item.getDescription());
                activity.startActivity(intent);

            }
        });
    }

    private boolean toBoolean (int n){
        return n!=0;
    }
    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ModelOfToDo> toDoList){
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        ModelOfToDo item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ModelOfToDo item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        bundle.putString("description", item.getDescription());
        bundle.putString("stage", item.getStage());
        bundle.putInt("status", item.getStatus());
        NewTaskAdd fragment = new NewTaskAdd();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), NewTaskAdd.TAG);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView textOfTask, textOfDescription, textOfStage;
        CheckBox checkBox;
        RelativeLayout parentLayout;
        Button startTimer;
        Context context;





        ViewHolder(View view) {
            super(view);

            checkBox = view.findViewById(R.id.todoCheckBox);
            parentLayout = view.findViewById(R.id.parent_layout);
            startTimer = view.findViewById(R.id.start_timer);
            textOfTask = view.findViewById(R.id.text_task);
            textOfDescription = view.findViewById(R.id.text_description);
            context = view.getContext();
            textOfStage = view.findViewById(R.id.stage_of_task);

         /*   startTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), TimerActivity.class);
                    context.startActivity(intent);
                    intent.putExtra(TASK_TEXT, String.valueOf(textOfTask.getText() ));
                }
            }); */



        }
    }
}


