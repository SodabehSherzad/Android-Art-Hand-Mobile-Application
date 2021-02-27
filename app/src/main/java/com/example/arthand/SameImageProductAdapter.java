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

public class SameImageProductAdapter extends RecyclerView.Adapter<SameImageProductAdapter.ViewHolder> {
    List<Model> list;
    Context context;
    public SameImageProductAdapter(Context context, List<Model> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public SameImageProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_detail_image_item, parent, false);
        return new SameImageProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SameImageProductAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getProductName());
        if(list.get(position).getProductImage().split("/").length==1){
            int imageId = context.getResources().getIdentifier(list.get(position).getProductImage(),
                    "drawable", context.getPackageName());
            holder.imageView.setImageResource(imageId);
        }
        else{
            Uri imageId = Uri.parse(list.get(position).getProductImage());
            holder.imageView.setImageURI(imageId);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", (Serializable) list.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
