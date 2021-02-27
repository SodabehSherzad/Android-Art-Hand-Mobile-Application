package com.example.arthand;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {
    Context context;
    ArrayList<Uri> list;

    public AddImageAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Uri> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_add_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if(list == null){
//            list.add();
//        }
        Uri uri = list.get(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }else{
            return list.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, close;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView = itemView.findViewById(R.id.addImage);
           close = itemView.findViewById(R.id.close);

           close.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   list.remove(getAdapterPosition());
                   notifyDataSetChanged();
               }
           });
        }

    }
}
