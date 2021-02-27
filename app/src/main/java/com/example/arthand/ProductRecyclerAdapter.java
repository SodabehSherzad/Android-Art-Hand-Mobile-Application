package com.example.arthand;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {
    List<Model> list;
    Context context;
    boolean favoriteCheck;
    public static int p_id;
    public ProductRecyclerAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerAdapter.ViewHolder holder, int position) {
        if(list.get(position).getProductImage().split("/").length==1){
            int imageId = context.getResources().getIdentifier(list.get(position).getProductImage(),
                    "drawable", context.getPackageName());
            holder.image.setImageResource(imageId);
        }
        else{
            Uri imageId = Uri.parse(list.get(position).getProductImage());
            holder.image.setImageURI(imageId);
        }

        holder.name.setText(list.get(position).getProductName());
        holder.number.setText("تعداد: " + list.get(position).getProductNumber());
        holder.price.setText(list.get(position).getProductPrice() + "$");

        favoriteCheck = MainActivity.databaseHelper.checkFavorite(list.get(position).getpUserId(), list.get(position).getProductId());

        if(favoriteCheck == true){
            holder.favoriteImage.setImageResource(R.drawable.ic_heart);
        }else {
            holder.favoriteImage.setImageResource(R.drawable.ic_favorite);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView image, favoriteImage;
        TextView name, number, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            favoriteImage = itemView.findViewById(R.id.favorite);
            name = itemView.findViewById(R.id.nameTextView);
            number = itemView.findViewById(R.id.numberTextView);
            price = itemView.findViewById(R.id.priceTextView);

            favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(favoriteCheck == true){
                        favoriteImage.setImageResource(R.drawable.ic_favorite);
                        MainActivity.databaseHelper.unsetFavorite(list.get(getAdapterPosition()).getpUserId(), list.get(getAdapterPosition()).getProductId());
                        favoriteCheck = MainActivity.databaseHelper.checkFavorite(list.get(getAdapterPosition()).getpUserId(), list.get(getAdapterPosition()).getProductId());
                    }else{
                        favoriteImage.setImageResource(R.drawable.ic_heart);
                        MainActivity.databaseHelper.setFavorite(list.get(getAdapterPosition()).getpUserId(), list.get(getAdapterPosition()).getProductId());
                        favoriteCheck = MainActivity.databaseHelper.checkFavorite(list.get(getAdapterPosition()).getpUserId(), list.get(getAdapterPosition()).getProductId());
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", (Serializable) list.get(getAdapterPosition()));
            p_id = list.get(getAdapterPosition()).getProductId();
            context.startActivity(intent);
        }
    }
}
