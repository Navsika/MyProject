package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CatActivity;
import com.example.project.MainActivity;
import com.example.project.Model.ModelOfItemInShop;
import com.example.project.Model.ModelOfToDo;
import com.example.project.R;
import com.example.project.ShopHelper;
import com.example.project.Utils.OpenHelper;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<AdapterOfToDo.ViewHolder> {
    private List<ModelOfItemInShop> shopList;
    private CatActivity activity;
    private Context mContext;



    @NonNull
    @Override
    public AdapterOfToDo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);
        return new AdapterOfToDo.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfToDo.ViewHolder holder, int position) {





    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{




        ViewHolder(View view) {
            super(view);





        }
    }
}
