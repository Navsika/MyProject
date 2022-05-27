package com.example.project.Inventory;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {

    private Map<ItemInInventory, Integer> inventory;

    public Inventory(Map<ItemInInventory, Integer> inventory){
        this.inventory = inventory;
    }

    public Map<ItemInInventory, Integer> getInventory(){
        return inventory;
    }

    public void addItem(ItemInInventory item){
        if (!inventory.containsKey(item))
            inventory.put(item, 0);

        inventory.put(item, inventory.get(item) + 1);
    }

    public void eatItem(ItemInInventory item){
        inventory.put(item, inventory.get(item) - 1);

        if(inventory.get(item)==0){
            inventory.remove(item);
        }
    }

}
