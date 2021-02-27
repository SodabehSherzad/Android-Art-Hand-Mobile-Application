package com.example.arthand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    List<Model> list;
    SubCategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView recyclerView;
    int category_id;
    TextView emptySubcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        emptySubcategory = findViewById(R.id.emptySubcategory);
        category_id = getIntent().getIntExtra("category_id", 0);

        reloadData();
    }

    private void reloadData() {
        list = MainActivity.databaseHelper.getSubCategory(category_id);
        if(list.size() == 0){
            emptySubcategory.setVisibility(View.VISIBLE);
        }else{
            emptySubcategory.setVisibility(View.GONE);
        }
        recyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerAdapter = new SubCategoryRecyclerAdapter(this, list);
        categoryRecyclerAdapter.notifyDataSetChanged();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoryRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Model> newList = new ArrayList<>();

                for (Model item : list){
                    if(item.getSubcategoryName().toLowerCase().contains(s.toLowerCase())){
                        newList.add(item);
                    }
                }

                categoryRecyclerAdapter.filterList(newList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}