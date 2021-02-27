package com.example.arthand;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class AddProductActivity extends AppCompatActivity {
    Button bSubmit;
    TextView errorMessage;
    Button chooseImage;
    AddImageAdapter addImageAdapter;
    RecyclerView recyclerView;
    int subcategory_id, product_id, getSubcategory_id;
    EditText pName, pDetail, pNumber, pSize, pWeight, pDimension, pColor, price;
    List<Uri> imageList = new LinkedList<>();
    List<String> subcategoryList, categoryList;
    String subCategoryName;
    Spinner subcategorySpinner;
    ArrayAdapter<String> subcategoryAdapter;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        errorMessage = findViewById(R.id.text_err_message);
        categoryList = MainActivity.databaseHelper.listOfCategory();

        final Spinner categorySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        subcategorySpinner = (Spinner) findViewById(R.id.spinner1);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subcategoryList = MainActivity.databaseHelper.listOfSubcategory(categorySpinner.getSelectedItem().toString());
                subcategoryAdapter = new ArrayAdapter<String>(AddProductActivity.this, android.R.layout.simple_spinner_item, subcategoryList);
                subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subcategorySpinner.setAdapter(subcategoryAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subcategoryList = new ArrayList<>();
                subcategoryList.add("Select the Category first");
            }
        });


        subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subCategoryName = adapterView.getSelectedItem().toString();
                getSubcategory_id = MainActivity.databaseHelper.subcategoryId(subCategoryName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subcategory_id = getIntent().getIntExtra("sub_id", 0);
        product_id = getIntent().getIntExtra("p_id", 0);
        bSubmit = findViewById(R.id.submit_button);
        pName = findViewById(R.id.product_name);
        pDetail = findViewById(R.id.product_detail);
        pNumber = findViewById(R.id.product_number);
        pSize = findViewById(R.id.product_size);
        pWeight = findViewById(R.id.product_weight);
        price = findViewById(R.id.product_price);
        pDimension = findViewById(R.id.product_dimension);
        pColor = findViewById(R.id.product_color);
        // handle submit button to preview the entered data

        chooseImage = findViewById(R.id.chooseImage);
        recyclerView = findViewById(R.id.addImageRecyclerView);

        addImageAdapter = new AddImageAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(addImageAdapter);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                insertdata(v);
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
    }

    private void insertdata(View v) {
        String name = pName.getText().toString();
        String detail = pDetail.getText().toString();
        String number = pNumber.getText().toString();
        String size = pSize.getText().toString();
        String weight = pWeight.getText().toString();
        String dimension = pDimension.getText().toString();
        String color = pColor.getText().toString();
        String product_price = price.getText().toString();

        if(imageList.size() == 0){
            uri = Uri.parse("design");
//            errorMessage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please choose an image!", Toast.LENGTH_LONG).show();

        }else{
            uri = imageList.get(0);
//            errorMessage.setVisibility(View.INVISIBLE);
        }

        if( name.length() >= 3 & detail.length() > 10 ) {
            MainActivity.databaseHelper.insertProduct(uri.toString(), getSubcategory_id, 1, name, detail, number, size, weight, dimension, color,
                    product_price);
//            Toast.makeText(this, "Please fill in Name and Detail of product!", Toast.LENGTH_LONG).show();
//            errorMessage.setVisibility(View.INVISIBLE);
            finish();
        }else{
            Toast.makeText(this, "Please fill in Name and Detail of product!", Toast.LENGTH_LONG).show();
//            errorMessage.setVisibility(View.VISIBLE);
        }

    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openBottomPicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AddProductActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void openBottomPicker() {
        TedBottomPicker.OnMultiImageSelectedListener listener = new TedBottomPicker.OnMultiImageSelectedListener() {
            @Override
            public void onImagesSelected(ArrayList<Uri> uriList) {
                imageList = uriList;
                addImageAdapter.setData(uriList);
            }
        };

        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(AddProductActivity.this)
                .setOnMultiImageSelectedListener(listener)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Image")
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

}