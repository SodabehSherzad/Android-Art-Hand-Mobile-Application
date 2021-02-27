package com.example.arthand.ui.profile;

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

public class ProfileFragment extends Fragment {
    List<ProfileViewModel> list;
    TextView emptyProfile;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        emptyProfile = root.findViewById(R.id.emptyProfile);
        list = MainActivity.databaseHelper.getUser();
        if(list.size() == 0){
            emptyProfile.setVisibility(View.VISIBLE);
        }else{
            emptyProfile.setVisibility(View.GONE);
        }
        RecyclerView userProfileRecyclerView = root.findViewById(R.id.userProfileRecyclerView);
        UserProfileAdapter userProfileAdapter = new UserProfileAdapter(getActivity(), list);
        userProfileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userProfileRecyclerView.setAdapter(userProfileAdapter);

        return root;
    }
}