package com.example.arthand.ui.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arthand.DatabaseHelper;
import com.example.arthand.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    static List<CartViewModel> list;
    Context context;
    DatabaseHelper databaseHelper;

    public CartAdapter( Context context, List<CartViewModel> list, DatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
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
        holder.price.setText(list.get(position).getProductPrice() + "");
        holder.countButton.setText(list.get(position).getCount() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, countButton;
        Button delete, add;
        ImageView image;
        int count = 1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            countButton = itemView.findViewById(R.id.count);
            add = itemView.findViewById(R.id.add);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if(count <= 1) {
                        new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_menu_delete)
                                .setTitle("Delete Note").setMessage("Are you sure to delete the product!")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        databaseHelper.unsetCart(list.get(getAdapterPosition()).getUser_id(),
                                                list.get(getAdapterPosition()).getProduct_id());
                                        list.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Deleted Product", Toast.LENGTH_LONG).show();
                                    }
                                }).setNegativeButton("No", null).show();
                    }else if(count > 0){
                        count--;
                        list.get(getAdapterPosition()).setCount(count);
                        notifyDataSetChanged();
//                        Toast.makeText(context, "Can not Delete Product", Toast.LENGTH_LONG).show();
                    }
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count++;
                    list.get(getAdapterPosition()).setCount(count);
                    notifyDataSetChanged();
                }
            });

        }
    }
}
