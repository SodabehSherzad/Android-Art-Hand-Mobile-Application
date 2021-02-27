package com.example.arthand.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;

import java.util.List;

public class FavoritesFragment extends Fragment {

    List<Model> list;
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    TextView emptyFavorite;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = root.findViewById(R.id.favoriteRecyclerView);
        emptyFavorite = root.findViewById(R.id.emptyFavorite);
        list = MainActivity.databaseHelper.getFavorite();
        if(list.size() == 0){
            emptyFavorite.setVisibility(View.VISIBLE);
        }else{
            emptyFavorite.setVisibility(View.GONE);
        }
        favoriteAdapter = new FavoriteAdapter(getActivity(), list, MainActivity.databaseHelper);
        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }
}