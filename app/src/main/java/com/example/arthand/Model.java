package com.example.arthand;

import java.io.Serializable;

public class Model implements Serializable {
    int pSubcategoryId, pUserId;
    String subcategoryName, subcategoryImage,
            comment;
    int subcategoryId, commentId, userId, productId;

    public Model(int subcategoryId, String subcategoryName, String subcategoryImage) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.subcategoryImage = subcategoryImage;
    }

    public Model(String comment, int productId, int userId, int commentId) {
        this.comment = comment;
        this.productId = productId;
        this.userId = userId;
        this.commentId = commentId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getSubcategoryImage() {
        return subcategoryImage;
    }

    public void setSubcategoryImage(String subcategoryImage) {
        this.subcategoryImage = subcategoryImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    String productName, productImage, productColor, productDetail, productDimension;
    int productNumber;
    double productSize, productWeight, productPrice;

    public Model(int productId, int pSubcategoryId, int pUserId, String productName, String productDetail, int productNumber, double productSize, double productWeight, String productDimension, String productColor, double productPrice, String productImage) {
        this.productId = productId;
        this.pSubcategoryId = pSubcategoryId;
        this.pUserId = pUserId;
        this.productName = productName;
        this.productImage = productImage;
        this.productColor = productColor;
        this.productDetail = productDetail;
        this.productNumber = productNumber;
        this.productSize = productSize;
        this.productDimension = productDimension;
        this.productWeight = productWeight;
        this.productPrice = productPrice;
    }

    public int getpSubcategoryId() {
        return pSubcategoryId;
    }

    public void setpSubcategoryId(int pSubcategoryId) {
        this.pSubcategoryId = pSubcategoryId;
    }

    public int getpUserId() {
        return pUserId;
    }

    public void setpUserId(int pUserId) {
        this.pUserId = pUserId;
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

    String order_status, order_date;

    public Model(int pUserId, int productId, String order_status, String order_date, String productName, String productDetail, int productNumber, double productSize, double productWeight, String productDimension, String productColor, double productPrice, String productImage) {
        this.order_status = order_status;
        this.order_date = order_date;
        this.productId = productId;
        this.pSubcategoryId = pSubcategoryId;
        this.pUserId = pUserId;
        this.productName = productName;
        this.productImage = productImage;
        this.productColor = productColor;
        this.productDetail = productDetail;
        this.productNumber = productNumber;
        this.productSize = productSize;
        this.productDimension = productDimension;
        this.productWeight = productWeight;
        this.productPrice = productPrice;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}