package com.example.arthand.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arthand.Model;
import com.example.arthand.SubCategoryActivity;
import com.example.arthand.R;

import java.util.List;

public class HomeCategoryRecyclerAdapter extends RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ViewHolder> {
    List<HomeViewModel> list;
    Context context;
    public HomeCategoryRecyclerAdapter(Context context, List<HomeViewModel> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getCategoryName());
        int imageId = context.getResources().getIdentifier(list.get(position).getCategoryImage(),
                "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageId);
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
            Intent intent = new Intent();
            intent.setClass(context, SubCategoryActivity.class);
            intent.putExtra("category_id", list.get(getAdapterPosition()).getCategoryId());
            context.startActivity(intent);
        }
    }

}
