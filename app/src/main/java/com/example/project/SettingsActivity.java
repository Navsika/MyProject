package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sp, sp1;
    private static final String SHARED_PREF_MONEY = "moneySharedPref";
    private static final String KEY_MONEY = "money";

    private static final String SHARED_PREF_TIME = "timeSharedPref";
    private static final String KEY_SHORT_TIME = "short";
    private static final String KEY_LONG_TIME = "long";

    BottomNavigationView bottomNavigationView;
    CardView card1, card2;
    Dialog dialog;
    TextView textOfWorkTime, textOfRestTime, moneySettings;

    public static int workTime ;
    public static int isRest ;
    public static int shortRestTime;
    public static int isWorkTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = getSharedPreferences(SHARED_PREF_MONEY, Context.MODE_PRIVATE);
        moneySettings = findViewById(R.id.money_settings_activity);
        moneySettings.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));





        dialog = new Dialog(this);

        sp1 = getSharedPreferences(SHARED_PREF_TIME, Context.MODE_PRIVATE);

        textOfWorkTime = findViewById(R.id.setting_text_of_work_time);
        textOfRestTime = findViewById(R.id.setting_text_of_short_time);

        textOfWorkTime.setText(String.valueOf(sp1.getInt(KEY_LONG_TIME, 0)));
        textOfRestTime.setText(String.valueOf(sp1.getInt(KEY_SHORT_TIME, 0)));

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkDialog();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestDialog();
            }
        });

       bottomNavigationView = findViewById(R.id.bottomNavigation);
       bottomNavigationView.setBackground(null);
       bottomNavigationView.setSelectedItemId(R.id.settings);

       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){
                   case R.id.pet:
                       startActivity(new Intent(getApplicationContext(), CatActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.settings:
                       return true;
                   case R.id.help:
                       startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                       overridePendingTransition(0, 0);
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


    private void openWorkDialog(){
        dialog.setContentView(R.layout.time_picker_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button buttonPlus = dialog.findViewById(R.id.buttonplus);
        Button buttonMinus = dialog.findViewById(R.id.buttonminus);
        TextView textOfWorkTimeInDialog = dialog.findViewById(R.id.text_of_minute_in_dialog);
        Button buttonSet = dialog.findViewById(R.id.set);
        workTime = Integer.valueOf(textOfWorkTime.getText().toString());
        textOfWorkTimeInDialog.setText(String.valueOf(sp1.getInt(KEY_LONG_TIME, 0)));

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workTime++;
                SharedPreferences.Editor editor = sp1.edit();
                editor.putInt(KEY_LONG_TIME, workTime);
                editor.apply();
                textOfWorkTimeInDialog.setText(String.valueOf(sp1.getInt(KEY_LONG_TIME, 0)));
                isWorkTime = sp1.getInt(KEY_LONG_TIME, 0);
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workTime>=1){
                    workTime--;
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putInt(KEY_LONG_TIME, workTime);
                    editor.apply();
                    textOfWorkTimeInDialog.setText(String.valueOf(sp1.getInt(KEY_LONG_TIME, 0)));
                    isWorkTime = sp1.getInt(KEY_LONG_TIME, 0);
                }else{
                    Toast.makeText(getApplicationContext(), "Время введено некорректно. Введите еще раз", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textOfWorkTime.setText(String.valueOf(sp1.getInt(KEY_LONG_TIME, 0)));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openRestDialog(){
        dialog.setContentView(R.layout.time_picker_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT) );

        Button buttonPlus = dialog.findViewById(R.id.buttonplus);
        Button buttonMinus = dialog.findViewById(R.id.buttonminus);
        TextView textOfShortRestInDialog = dialog.findViewById(R.id.text_of_minute_in_dialog);
        Button buttonSet = dialog.findViewById(R.id.set);
        shortRestTime = Integer.valueOf(textOfRestTime.getText().toString());
        textOfShortRestInDialog.setText(String.valueOf(sp1.getInt(KEY_SHORT_TIME, 0)));


        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortRestTime++;
                SharedPreferences.Editor editor = sp1.edit();
                editor.putInt(KEY_SHORT_TIME, shortRestTime);
                editor.apply();
                textOfShortRestInDialog.setText(String.valueOf(sp1.getInt(KEY_SHORT_TIME, 0)));
                isRest = sp1.getInt(KEY_SHORT_TIME, 0);
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shortRestTime>=1){
                    shortRestTime--;
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putInt(KEY_SHORT_TIME, shortRestTime);
                    editor.apply();
                    textOfShortRestInDialog.setText(String.valueOf(sp1.getInt(KEY_SHORT_TIME, 0)));
                    isRest = sp1.getInt(KEY_SHORT_TIME, 0);
                }else{
                    Toast.makeText(getApplicationContext(), "Время введено некорректно. Введите еще раз", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textOfRestTime.setText(String.valueOf(sp1.getInt(KEY_SHORT_TIME, 0)));
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}