package com.example.arthand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    public static int subcategory_id;
    List<Model> list = new LinkedList<>();
    RecyclerView recyclerView;
    ProductRecyclerAdapter productRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView = findViewById(R.id.productRecyclerView);
        subcategory_id = getIntent().getIntExtra("subcategory_id", 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerAdapter = new ProductRecyclerAdapter(this, list);
        recyclerView.setAdapter(productRecyclerAdapter);
        reloadData();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        System.out.println("Resume");
//        reloadData();
//    }

    private void reloadData() {
        list.clear();
        List<Model> tempList =  MainActivity.databaseHelper.getProduct(subcategory_id);
        for(Model model: tempList){
            list.add(model);
        }
        productRecyclerAdapter.notifyDataSetChanged();

    }

//    public void setData(View view) {
//        Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
//        intent.putExtra("sub_id", subcategory_id);
//        intent.putExtra("p_id", productRecyclerAdapter.p_id);
//        startActivity(intent);
//    }
}