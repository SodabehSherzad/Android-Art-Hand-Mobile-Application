package com.example.arthand.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthand.AddImageAdapter;
import com.example.arthand.MainActivity;
import com.example.arthand.Model;
import com.example.arthand.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class EditUserItemActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 1;
    Button bEdit;
    TextView errorMessage;
    ImageView chooseImage;

    int subcategory_id, product_id, getSubcategory_id;

    EditText pName, pDetail, pNumber, pSize, pWeight, pDimension, pColor, price;

    List<String> subcategoryList, categoryList;
    String subCategoryName;
    Spinner subcategorySpinner;
    ArrayAdapter<String> subcategoryAdapter;

    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_item);

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
                subcategoryAdapter = new ArrayAdapter<String>(EditUserItemActivity.this, android.R.layout.simple_spinner_item, subcategoryList);
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

        // handle submit button to preview the entered data

        chooseImage = findViewById(R.id.chooseImage);
        bEdit = findViewById(R.id.edit_button);

        initializeData();

        bEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                updateData(v);
            }
        });

    }

    public void initializeData(){
        pName = findViewById(R.id.product_name);
        pDetail = findViewById(R.id.product_detail);
        pNumber = findViewById(R.id.product_number);
        pSize = findViewById(R.id.product_size);
        pWeight = findViewById(R.id.product_weight);
        price = findViewById(R.id.product_price);
        pDimension = findViewById(R.id.product_dimension);
        pColor = findViewById(R.id.product_color);

        Model data = (Model) getIntent().getSerializableExtra("product");
        product_id = data.getProductId();
        subcategory_id = data.getpSubcategoryId();

        if(data.getProductImage().split("/").length==1){
            int imageId = this.getResources().getIdentifier(data.getProductImage(),
                    "drawable", this.getPackageName());
            chooseImage.setImageResource(imageId);
        }
        else{
            Uri imageId = Uri.parse(data.getProductImage());
            chooseImage.setImageURI(imageId);
        }

        pName.setText(data.getProductName());
        pDetail.setText(data.getProductDetail());
        pNumber.setText(" " + data.getProductNumber());
        price.setText(data.getProductPrice() + "");
        pWeight.setText( " " + data.getProductWeight());
        pSize.setText(" " + data.getProductSize());
        pDimension.setText("  " + data.getProductDimension());
        pColor.setText(" " + data.getProductColor());

    }

    public void choosePhoto(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }else{
                openGallery();
            }
        } else{
            openGallery();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(EditUserItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
    private void openGallery() {
        Intent openGallery = new Intent(Intent.ACTION_GET_CONTENT);
        openGallery.setType("image/*");
        startActivityForResult(openGallery, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE){
            uri = data.getData();
            chooseImage.setImageURI(uri);
            chooseImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private void updateData(View v) {
            String name = pName.getText().toString();
            String detail = pDetail.getText().toString();
            String number = pNumber.getText().toString();
            String size = pSize.getText().toString();
            String weight = pWeight.getText().toString();
            String dimension = pDimension.getText().toString();
            String color = pColor.getText().toString();
            String product_price = price.getText().toString();

            if(uri == null){
                Model data = (Model) getIntent().getSerializableExtra("product");

                if(data.getProductImage().split("/").length==1){
                    int imageId = this.getResources().getIdentifier(data.getProductImage(),
                            "drawable", this.getPackageName());
                    uri = Uri.parse(String.valueOf(imageId));
                }
                else{
                   uri = Uri.parse(data.getProductImage());
                }
            }

            if( name.length() >= 3 & detail.length() > 10 ) {
                MainActivity.databaseHelper.updateProduct(uri.toString(), product_id, getSubcategory_id, name, detail, number, size, weight, dimension, color,
                        product_price);
                finish();
            }else{
                Toast.makeText(this, "Please fill in Name and Detail of product!", Toast.LENGTH_LONG).show();
            }

        }

    }