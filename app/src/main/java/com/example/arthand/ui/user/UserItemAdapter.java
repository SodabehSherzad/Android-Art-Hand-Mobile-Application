package com.example.arthand.ui.user;

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
import com.example.arthand.Model;
import com.example.arthand.ProductDetailActivity;
import com.example.arthand.R;

import java.io.Serializable;
import java.util.List;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {
    List<Model> list;
    Context context;
    public static int p_id;
    public UserItemAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_user_item, parent, false);
        return new UserItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemAdapter.ViewHolder holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView image, editImage;
        TextView name, number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            editImage = itemView.findViewById(R.id.edit);
            name = itemView.findViewById(R.id.nameTextView);
            number = itemView.findViewById(R.id.numberTextView);

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditUserItemActivity.class);
                    intent.putExtra("product", (Serializable) list.get(getAdapterPosition()));
                    p_id = list.get(getAdapterPosition()).getProductId();
                    context.startActivity(intent);
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

