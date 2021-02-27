package com.example.arthand.ui.favorites;

import java.io.Serializable;

public class FavoritesViewModel implements Serializable {
    int id, user_id, product_id;
    String productName, productImage, productColor, productDetail, productDimension;
    int productNumber;
    double productSize, productWeight, productPrice;

    public FavoritesViewModel(int id, int user_id, int product_id, String productName, String productImage, String productColor, String productDetail, String productDimension, int productNumber, double productSize, double productWeight, double productPrice) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.productName = productName;
        this.productImage = productImage;
        this.productColor = productColor;
        this.productDetail = productDetail;
        this.productDimension = productDimension;
        this.productNumber = productNumber;
        this.productSize = productSize;
        this.productWeight = productWeight;
        this.productPrice = productPrice;
    }

    public FavoritesViewModel(int id, int user_id, int product_id) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductDimension() {
        return productDimension;
    }

    public void setProductDimension(String productDimension) {
        this.productDimension = productDimension;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public double getProductSize() {
        return productSize;
    }

    public void setProductSize(double productSize) {
        this.productSize = productSize;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}