package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AboutActivity extends AppCompatActivity {
    SharedPreferences sp;
    private static final String SHARED_PREF_MONEY = "moneySharedPref";
    private static final String KEY_MONEY = "money";

    BottomNavigationView bottomNavigationView;
    TextView moneyAbout;
    TextView kanban, aboutKanaban, pomodoro, aboutPomodoro, m135, about135;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        kanban = findViewById(R.id.metod_kanban);
        pomodoro = findViewById(R.id.metod_pomodoro);
        m135 = findViewById(R.id.metod_1_3_5);
        aboutKanaban = findViewById(R.id.kanban);
        aboutPomodoro = findViewById(R.id.pomodoro);
        about135 = findViewById(R.id.m135);





        sp = getSharedPreferences(SHARED_PREF_MONEY, Context.MODE_PRIVATE);
        moneyAbout = findViewById(R.id.money_about_activity);
        moneyAbout.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setSelectedItemId(R.id.help);

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
                        return true;
                    case R.id.tasks:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


    }
}