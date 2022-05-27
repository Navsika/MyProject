package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Inventory.Inventory;
import com.example.project.R;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private Context context;
    private Inventory inventory;

    public InventoryAdapter(Inventory inventory){
        this.inventory = inventory;
    }

    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);
        return new InventoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textItem, descriptionItem;
        Button eat;
        ImageView imageItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textItem = itemView.findViewById(R.id.text_of_item);
            descriptionItem = itemView.findViewById(R.id.description_of_item);
            eat = itemView.findViewById(R.id.eat);
            imageItem = itemView.findViewById(R.id.image_of_item);

        }
    }
}
