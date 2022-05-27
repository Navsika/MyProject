package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.ShopAdapter;
import com.example.project.Model.CatModel;
import com.example.project.Model.MoneyClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CatActivity extends AppCompatActivity {
    SharedPreferences sp;
    private static final String SHARED_PREF_MONEY = "moneySharedPref";
    private static final String KEY_MONEY = "money";



    BottomNavigationView bottomNavigationView;
    private RecyclerView shopRecycler;
    TextView moneyCat;
    Dialog dialog, dialog2, dialog3;
    ImageView catImage;
    EditText textCatName;
    Button toStore, toInventory;

    private static final String FILE_NAME_OF_CAT = "nameCat.txt";
    private static final String FILE_FULL_OF_CAT = "fullCat.txt";
    private static final String FILE_HAPPY_OF_CAT = "happyCat.txt";
    private String catName;
    private int full, happy;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter shopAdapter;

    String[] productNames = {"Тайяки", "Сэндвич с беконом и яйцом", "Роллы с лососем"};
    String[] descriptionProduct = {"+ 5 к сытости", "+ 10 к сытости", "+ 15 к сытости"};
    int[] priceProduct = {10, 20, 35};
    int[] imagesProduct = {R.drawable.fishes, R.drawable.sandwich, R.drawable.sushi};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        toStore = findViewById(R.id.toStore);
        dialog2 = new Dialog(this);
        toStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.setContentView(R.layout.shop_layout);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(179, 160, 232)));
                shopRecycler = dialog2.findViewById(R.id.shop_recycler);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                shopRecycler.setLayoutManager(layoutManager);
                shopAdapter = new ShopAdapter(getApplicationContext(), productNames, descriptionProduct, priceProduct, imagesProduct);
                shopRecycler.setAdapter(shopAdapter);




                Button back = dialog2.findViewById(R.id.back_from_shop);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });

                dialog2.show();

            }
        });


        toInventory = findViewById(R.id.toInventory);
        toInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.setContentView(R.layout.shop_layout);
            }
        });





        dialog = new Dialog(this);
        dialog.setContentView(R.layout.cat_information);

        textCatName = dialog.findViewById(R.id.cat_name);

        catImage = findViewById(R.id.cat);
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCatDialog();
                load(v);


            }
        });

        sp = getSharedPreferences(SHARED_PREF_MONEY, Context.MODE_PRIVATE);
        moneyCat = findViewById(R.id.money_cat_activity);
        moneyCat.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));





        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setSelectedItemId(R.id.pet);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pet:
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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


    }

    private void openCatDialog(){

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            Button okay = dialog.findViewById(R.id.okay);
            String catname = textCatName.getText().toString();

        ProgressBar progressFull = dialog.findViewById(R.id.progressBar_full);
        ProgressBar progressHappy = dialog.findViewById(R.id.progressBar_happy);
        progressHappy.setMax(100);
        progressFull.setMax(100);
        progressFull.setProgress(30);
        progressHappy.setProgress(60);
     //   progressFull.setProgress(cat.getFull());
      //  progressHappy.setProgress(cat.getHappy());
      //  cat.setHappy(100);
     //   cat.setFull(100);

        okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    save(v);
                }
            });
            dialog.show();
    }

    public void load(View v){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME_OF_CAT);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null){
                sb.append(text);
            }

            textCatName.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void save(View v){
        String text = textCatName.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME_OF_CAT, MODE_PRIVATE);
            fos.write(text.getBytes());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}

