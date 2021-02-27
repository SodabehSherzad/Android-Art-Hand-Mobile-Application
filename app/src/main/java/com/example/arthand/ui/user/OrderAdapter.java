package com.example.arthand.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.arthand.ProductDetailActivity;
import com.example.arthand.R;

import java.io.Serializable;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    static List<Model> list;
    Context context;
    DatabaseHelper databaseHelper;

    public OrderAdapter( Context context, List<Model> list, DatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_order_item, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

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
        holder.date.setText(list.get(position).getOrder_date());
        holder.price.setText(list.get(position).getProductPrice() + "$");
    }

    @Override
    public int getItemCount() {
        return list.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, price, delete, date;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_menu_delete)
                            .setTitle("Delete Note").setMessage("Are you sure to delete the product!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseHelper.unsetOrder(list.get(getAdapterPosition()).getpUserId(),
                                            list.get(getAdapterPosition()).getProductId());
                                    list.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted Product", Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton("No", null).show();
                }
            });

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
