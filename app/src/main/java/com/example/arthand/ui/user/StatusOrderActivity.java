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

public class StatusOrderActivity extends AppCompatActivity {
    List<Model> list;
    RecyclerView recyclerView;
    StatusOrderAdapter statusOrderAdapter;
    TextView emptyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_order);
        emptyOrder = findViewById(R.id.emptyOrder);
        String status = getIntent().getStringExtra("status");
        recyclerView = findViewById(R.id.recyclerView);
        if(status.equals("incomplete")){
            list = MainActivity.databaseHelper.getStatusOrder(status);
            this.setTitle("Incomplete Orders");
        }else if(status.equals("completed")){
            list = MainActivity.databaseHelper.getStatusOrder(status);
            this.setTitle("Completed Orders");
        }

        if(list.size() == 0){
            emptyOrder.setVisibility(View.VISIBLE);
        }else{
            emptyOrder.setVisibility(View.GONE);
        }
        statusOrderAdapter = new StatusOrderAdapter(this, list, MainActivity.databaseHelper);
        recyclerView.setAdapter(statusOrderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}