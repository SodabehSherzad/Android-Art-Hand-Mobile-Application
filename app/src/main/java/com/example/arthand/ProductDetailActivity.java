package com.example.arthand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arthand.ui.cart.CartViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView favoriteImage, image;
    TextView name, detail, size, number, price, weight, dimension, color, count;
    boolean favoriteCheck, cartCheck;
    int product_id, user_id, subcategory_id;
    Button buyBtn;
    ImageView addToCartBtn;
    List<Model> imageList = new LinkedList<>();

    RecyclerView recyclerView;
    SameImageProductAdapter adapter;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        detail = findViewById(R.id.detail);
        number = findViewById(R.id.number);
        price = findViewById(R.id.price);
        weight = findViewById(R.id.weight);
        dimension = findViewById(R.id.dimension);
        color = findViewById(R.id.color);
        size = findViewById(R.id.size);

        count = findViewById(R.id.count);

        favoriteImage = findViewById(R.id.favorite);

        addToCartBtn = findViewById(R.id.addToCartBtn);
        buyBtn = findViewById(R.id.buyBtn);

         getProductDetail();

//        check = false;
        favoriteCheck = MainActivity.databaseHelper.checkFavorite(user_id, product_id);
        cartCheck = MainActivity.databaseHelper.checkCart(user_id, product_id);
//        Toast.makeText(this,"like: " + check, Toast.LENGTH_LONG ).show();

        if(favoriteCheck == true){
            favoriteImage.setImageResource(R.drawable.ic_heart);
        }else {
            favoriteImage.setImageResource(R.drawable.ic_favorite);
        }

//        if(cartCheck == true){
//            Log.d("", "onCreate: "+ cartCheck);
////            addToCartBtn.setText("اضافه شده است به سبد خرید");
////            addToCartBtn.setBackgroundColor(addToCartBtn.getContext().getResources().getColor(R.color.colorDisable));
////            addToCartBtn.setTextColor(getResources().getColor(R.color.colorWhite));
//            addToCartBtn.setClickable(false);
//
//        }else {
//            Log.d("", "onCreate: "+ cartCheck);
////            addToCartBtn.setText("اضافه کردن به سبد خرید");
////            addToCartBtn.setBackgroundColor(addToCartBtn.getContext().getResources().getColor(R.color.colorPrimaryDark));
////            addToCartBtn.setTextColor(getResources().getColor(R.color.colorWhite));
//            addToCartBtn.setClickable(true);
//        }
        buyBtn.setText("خرید");
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS", Locale.ENGLISH).format(date);
                if(cartCheck == true){
                    MainActivity.databaseHelper.setOrder(user_id, format, product_id, "incomplete");
                    buyBtn.setBackgroundColor(buyBtn.getContext().getResources().getColor(R.color.colorDisable));
                    buyBtn.setText("خریده شد");
                    buyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    buyBtn.setClickable(true);
                }else{
                    MainActivity.databaseHelper.setOrder(user_id, format, product_id, "completed");
                    buyBtn.setBackgroundColor(buyBtn.getContext().getResources().getColor(R.color.colorDisable));
                    buyBtn.setText("خریده شد");
                    buyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    buyBtn.setClickable(true);
                }
            }
        });

        recyclerView = findViewById(R.id.imageProductRecyclerView);
        adapter = new SameImageProductAdapter(this, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        reloadData();
    }

    private void reloadData() {
        imageList.clear();
        List<Model> tempList = MainActivity.databaseHelper.getProduct();
        for (Model model : tempList) {
            imageList.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    private void getProductDetail() {
        Model data = (Model) getIntent().getSerializableExtra("product");
        product_id = data.getProductId();
        user_id = data.getpUserId();
        subcategory_id = data.getpSubcategoryId();

        if(data.getProductImage().split("/").length==1){
            int imageId = this.getResources().getIdentifier(data.getProductImage(),
                    "drawable", this.getPackageName());
            image.setImageResource(imageId);
        }
        else{
            Uri imageId = Uri.parse(data.getProductImage());
            image.setImageURI(imageId);
        }

        name.setText(data.getProductName());
        detail.setText(data.getProductDetail());
        number.setText("تعداد: " + data.getProductNumber());
        price.setText(data.getProductPrice() + "$");
        weight.setText( "وزن: " + data.getProductWeight());
        size.setText("سایز: " + data.getProductSize());
        dimension.setText("طول و عرض: " + data.getProductDimension());
        color.setText("رنگ: " + data.getProductColor());
    }

    public void addFavorite(View view) {
        if(favoriteCheck == true){
            favoriteImage.setImageResource(R.drawable.ic_favorite);
            MainActivity.databaseHelper.unsetFavorite(user_id, product_id);
            favoriteCheck = MainActivity.databaseHelper.checkFavorite(user_id, product_id);
        }else{
            favoriteImage.setImageResource(R.drawable.ic_heart);
            MainActivity.databaseHelper.setFavorite(user_id, product_id);
            favoriteCheck = MainActivity.databaseHelper.checkFavorite(user_id, product_id);
        }
    }

    public void addCart(View view){
        i++;
        count.setText(i + "");
        if(cartCheck == false){
            MainActivity.databaseHelper.setCart(user_id, product_id);
//            addToCartBtn.setText("اضافه شده است به سبد خرید");
//            addToCartBtn.setBackgroundColor(addToCartBtn.getContext().getResources().getColor(R.color.colorDisable));
//            addToCartBtn.setTextColor(getResources().getColor(R.color.colorWhite));
            cartCheck = MainActivity.databaseHelper.checkCart(user_id, product_id);

        }else{
            MainActivity.databaseHelper.unsetCart(user_id, product_id);
//            addToCartBtn.setText("اضافه کردن به سبد خرید");
//            addToCartBtn.setBackgroundColor(addToCartBtn.getContext().getResources().getColor(R.color.colorPrimaryDark));
//            addToCartBtn.setTextColor(getResources().getColor(R.color.colorWhite));
            cartCheck = MainActivity.databaseHelper.checkCart(user_id, product_id);

        }
    }
}