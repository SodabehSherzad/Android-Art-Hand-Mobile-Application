package com.example.arthand;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter<SubCategoryRecyclerAdapter.ViewHolder> {
    List<Model> list;
    Context context;
    public SubCategoryRecyclerAdapter(Context context, List<Model> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_sub_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getSubcategoryName());
        int imageId = context.getResources().getIdentifier(list.get(position).getSubcategoryImage(),
                "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Model> newList){
        list = newList;
        notifyDataSetChanged();
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
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("subcategory_id", list.get(getAdapterPosition()).getSubcategoryId());
            context.startActivity(intent);
        }
    }


}
