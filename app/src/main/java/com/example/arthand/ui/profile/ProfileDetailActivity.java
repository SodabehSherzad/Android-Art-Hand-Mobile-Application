package com.example.arthand.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;

import java.util.LinkedList;
import java.util.List;

public class ProfileDetailActivity extends AppCompatActivity {
    TextView username, address;
    RecyclerView profileProductRecyclerView;
    ProfileProductAdapter profileProductAdapter;
    List<Model> profileProductList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        username = findViewById(R.id.username);
        address = findViewById(R.id.address);

        username.setText(getIntent().getStringExtra("username"));
        address.setText(getIntent().getStringExtra("address"));

        profileProductRecyclerView = findViewById(R.id.profileProductRecyclerView);
        profileProductAdapter = new ProfileProductAdapter(this, profileProductList);
        profileProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        profileProductRecyclerView.setAdapter(profileProductAdapter);

        reloadData();
    }

    private void reloadData() {
        profileProductList.clear();
        List<Model> tempList = MainActivity.databaseHelper.getProduct();
        for (Model model : tempList) {
            profileProductList.add(model);
        }
        profileProductAdapter.notifyDataSetChanged();
    }
}