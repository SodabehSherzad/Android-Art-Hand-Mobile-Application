package com.example.arthand.ui.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arthand.MainActivity;
import com.example.arthand.R;

import java.util.List;

public class CartFragment extends Fragment {

    List<CartViewModel> list;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView emptyCart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = root.findViewById(R.id.cartRecyclerView);
        emptyCart = root.findViewById(R.id.emptyCart);
        list = MainActivity.databaseHelper.getCart();
        if(list.size() == 0){
            emptyCart.setVisibility(View.VISIBLE);
        }else{
            emptyCart.setVisibility(View.GONE);
        }
        cartAdapter = new CartAdapter(getActivity(), list, MainActivity.databaseHelper);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }
}