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

import com.example.project.CatActivity;
import com.example.project.Model.ModelOfItemInShop;
import com.example.project.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ModelOfItemInShop> shopList;
    private CatActivity activity;
    private Context mContext;
    String[] productNames;
    String[] descriptionProduct;
    int[] priceProduct;
    int[] imagesProduct;

    public ShopAdapter(Context context, String[] productNames, String[] descriptionProduct, int[] priceProduct, int[] imagesProduct){
        this.mContext = context;
        this. priceProduct = priceProduct;
        this.productNames = productNames;
        this.descriptionProduct = descriptionProduct;
        this.imagesProduct = imagesProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textProduct.setText(productNames[position]);
        holder.textDescription.setText(descriptionProduct[position]);
        holder.price.setText(String.valueOf(priceProduct[position]));
        holder.image.setImageResource(imagesProduct[position]);

    }


    @Override
    public int getItemCount() {
        return productNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textProduct, textDescription, price;
        Button buy;
        ImageView image;
        ViewHolder(View view) {
            super(view);

            textProduct = view.findViewById(R.id.text_of_product);
            textDescription = view.findViewById(R.id.description_of_product);
            price = view.findViewById(R.id.price_of_product);
            buy = view.findViewById(R.id.buy);
            image = view.findViewById(R.id.image_of_product);

            buy = view.findViewById(R.id.buy);










        }
    }
}
