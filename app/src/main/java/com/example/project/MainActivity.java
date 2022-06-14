package com.example.project;

import static com.example.project.Adapter.AdapterOfToDo.coin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project.Adapter.AdapterOfToDo;
import com.example.project.Model.CatModel;
import com.example.project.Model.ModelOfToDo;
import com.example.project.Model.MoneyClass;
import com.example.project.Utils.OpenHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    SharedPreferences sp;
    private static final String SHARED_PREF_MONEY = "moneySharedPref";
    private static final String KEY_MONEY = "money";

    private OpenHelper db;

    private RecyclerView tasksRecycler;
    private AdapterOfToDo tasksAdapter;
    private FloatingActionButton fab;

    private List<ModelOfToDo> tasksList;


    BottomNavigationView bottomNavigationView;
    TextView moneyMain;

    private static int coin1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(SHARED_PREF_MONEY, Context.MODE_PRIVATE);
        moneyMain = findViewById(R.id.money_main_activity);
        moneyMain.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));



        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setSelectedItemId(R.id.tasks);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pet:
                        startActivity(new Intent(getApplicationContext(), CatActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tasks:
                        return true;
                }

                return false;
            }
        });


        db = new OpenHelper(this);
        db.openDatabase();

        tasksRecycler = findViewById(R.id.tasksRecyclerView);
        tasksRecycler.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new AdapterOfToDo(db, MainActivity.this ,getApplicationContext(),tasksRecycler);
        tasksRecycler.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecycler);

        fab = findViewById(R.id.fab);

        tasksList = db.getAllTasks();



        Collections.sort(tasksList, new Comparator<ModelOfToDo>() {
            @Override
            public int compare(ModelOfToDo o1, ModelOfToDo o2) {

                return o1.getStage().compareTo(o2.getStage());
            }
        });
        Collections.reverse(tasksList);

        tasksAdapter.setTasks(tasksList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTaskAdd.newInstance().show(getSupportFragmentManager(), NewTaskAdd.TAG);
            }
        });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged();
    }
}