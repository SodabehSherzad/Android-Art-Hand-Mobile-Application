package com.example.arthand.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;

import java.util.LinkedList;
import java.util.List;

public class UserFragment extends Fragment {
    CardView cardView;
    LinearLayout linearLayout, complete, incomplete;

    List<Model> userLikeList = new LinkedList<>();

    RecyclerView userLikeRecyclerView;
    UserLikeAdapter userLikeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        complete = view.findViewById(R.id.complete);
        incomplete = view.findViewById(R.id.incomplete);

        cardView = view.findViewById(R.id.cardView);
        linearLayout = view.findViewById(R.id.linearLayout);

        userLikeRecyclerView = view.findViewById(R.id.userLikeRecyclerView);
        userLikeAdapter = new UserLikeAdapter(getActivity(), userLikeList);
        userLikeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        userLikeRecyclerView.setAdapter(userLikeAdapter);

        reloadData();
        userDetail();
        showAllOrders();
        showCompletedOrder("completed");
        showIncompleteOrder("incomplete");

        return view;
    }

    private void reloadData() {
        userLikeList.clear();
        List<Model> tempList = MainActivity.databaseHelper.getFavorite();
        for (Model model : tempList) {
            userLikeList.add(model);
        }
        userLikeAdapter.notifyDataSetChanged();
    }

    public void showOrder(View view){
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);
    }

    public void showAllOrders(){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrder(view);
            }
        });
    }

    public void userDetail(){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showCompletedOrder(final String status){
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatusOrderActivity.class);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });
    }

    public void showIncompleteOrder(final String status){
        incomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatusOrderActivity.class);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });
    }
}