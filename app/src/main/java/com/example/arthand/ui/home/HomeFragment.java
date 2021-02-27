package com.example.arthand.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<HomeViewModel> imageList = new LinkedList<>();
    List<HomeViewModel> categoryList = new LinkedList<>();
    List<Model> itemList = new LinkedList<>();

    RecyclerView homeCategoryRecyclerView;
    HomeCategoryRecyclerAdapter homeCategoryRecyclerAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    RecyclerView homeNewCategoryRecyclerView;
    HomeNewItemsRecyclerAdapter HomeNewCategoryRecyclerAdapter;

    CarouselView carouselView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = root.findViewById(R.id.carousel);

        homeCategoryRecyclerView = root.findViewById(R.id.homeCategoryRecyclerView);
        homeCategoryRecyclerAdapter = new HomeCategoryRecyclerAdapter(getActivity(), categoryList);
//        homeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeCategoryRecyclerView.setAdapter(homeCategoryRecyclerAdapter);
      staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        homeCategoryRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        homeNewCategoryRecyclerView = root.findViewById(R.id.homeNewCategoryRecyclerView);
        HomeNewCategoryRecyclerAdapter = new HomeNewItemsRecyclerAdapter(getActivity(), itemList);
        homeNewCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeNewCategoryRecyclerView.setAdapter(HomeNewCategoryRecyclerAdapter);

        reloadData();

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
//       System.out.println("Resume");
        reloadData();
    }

    private void reloadData() {
        categoryList.clear();
        List<HomeViewModel> tempList =  MainActivity.databaseHelper.getCategory();
        for(HomeViewModel model: tempList){
            categoryList.add(model);
        }
        homeCategoryRecyclerAdapter.notifyDataSetChanged();

        itemList.clear();
        List<Model> tempList2 =  MainActivity.databaseHelper.getProduct();
        for(Model model: tempList2){
            itemList.add(model);
        }
        HomeNewCategoryRecyclerAdapter.notifyDataSetChanged();

        imageList.clear();
        List<HomeViewModel> tempList1 =  MainActivity.databaseHelper.getImage();
        for(HomeViewModel model: tempList1){
            imageList.add(model);
        }

        carouselView.setPageCount(imageList.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                if(imageList.get(position).getImage().split("/").length==1){
                    int imageId = getActivity().getResources().getIdentifier(imageList.get(position).getImage(), "drawable", getActivity().getPackageName());
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageResource(imageId);
                }
                else{
                    Uri imageId = Uri.parse(imageList.get(position).getImage());
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageURI(imageId);
                }
            }
        });
    }
}