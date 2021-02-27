package com.example.arthand.ui.favorites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arthand.DatabaseHelper;
import com.example.arthand.Model;
import com.example.arthand.R;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    static List<Model> list;
    Context context;
    DatabaseHelper databaseHelper;
    public FavoriteAdapter(Context context, List<Model> list, DatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        if(list.get(position).getProductImage().split("/").length==1){
            int imageId = context.getResources().getIdentifier(list.get(position).getProductImage(), "drawable", context.getPackageName());
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.image.setImageResource(imageId);
        }
        else{
            Uri imageId = Uri.parse(list.get(position).getProductImage());
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.image.setImageURI(imageId);
        }
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductPrice() + "$");
    }

    @Override
    public int getItemCount() {
        return list.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, delete, price;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);

            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_menu_delete)
                            .setTitle("Delete Note").setMessage("Are you sure to delete the product!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseHelper.unsetFavorite(list.get(getAdapterPosition()).getpUserId(),
                                            list.get(getAdapterPosition()).getProductId());
                                    list.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted Product", Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton("No", null).show();
                }
            });
        }
    }
}
