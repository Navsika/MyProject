package com.example.project.Model;



public class MoneyClass {
    private static int money;

    public void plusMoney(int coin){
        this.money+=coin;
    }
    public void minusMoney(int coin){
        this.money-=coin;
    }
    public int getAmountMoney(){
        return money;
    }




}
