package com.example.project.Model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CatModel {
   private String name;
  private  int full, happy;

   public String getName(){
      return name;
   }
   public void setName(String name){
       this.name = name;
   }

   public int getFull(){
        return full;
   }
   public void addFull(int fullPoint){
        this.full+=fullPoint;
   }
   public void minusFull(){
       this.full-=10;

   }
    public void setFull(int full){
        this.full = full;
    }

    public int getHappy(){
        return happy;
    }
    public void addHappy(int happyPoint){
        this.happy+=happyPoint;
    }
    public void minusHappy(){
        this.happy-=10;
    }
    public void setHappy(int happy){
        this.happy = happy;
    }


}
