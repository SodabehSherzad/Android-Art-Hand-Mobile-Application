package com.example.arthand.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.ProductRecyclerAdapter;
import com.example.arthand.R;

import java.util.LinkedList;
import java.util.List;

public class UserItemActivity extends AppCompatActivity {
    List<Model> list = new LinkedList<>();
    RecyclerView recyclerView;
    UserItemAdapter userItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item);

        recyclerView = findViewById(R.id.productRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userItemAdapter = new UserItemAdapter(this, list);
        recyclerView.setAdapter(userItemAdapter);

        reloadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }
    public void reloadData() {
        list.clear();
        List<Model> tempList =  MainActivity.databaseHelper.getProduct();
        for(Model model: tempList){
            list.add(model);
        }
        userItemAdapter.notifyDataSetChanged();
    }
}