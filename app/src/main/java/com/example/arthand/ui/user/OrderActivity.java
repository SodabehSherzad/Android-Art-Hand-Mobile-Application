package com.example.arthand.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    List<Model> list;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    TextView emptyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.orderRecyclerView);
        emptyOrder = findViewById(R.id.emptyOrder);
        list = MainActivity.databaseHelper.getOrder();
        if(list.size() == 0){
            emptyOrder.setVisibility(View.VISIBLE);
        }else{
            emptyOrder.setVisibility(View.GONE);
        }
        orderAdapter = new OrderAdapter(this, list, MainActivity.databaseHelper);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}