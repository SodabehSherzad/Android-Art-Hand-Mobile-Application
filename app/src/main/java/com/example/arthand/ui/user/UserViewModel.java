package com.example.arthand.ui.user;

public class UserViewModel {
    // TODO: Implement the ViewModel
    int order_user_id, order_product_id;
    String order_name, order_image;
    double order_price;

    public UserViewModel(int order_user_id, int order_product_id, String order_name, double order_price, String order_image) {
        this.order_user_id = order_user_id;
        this.order_product_id = order_product_id;
        this.order_name = order_name;
        this.order_image = order_image;
        this.order_price = order_price;
    }



    public int getOrder_user_id() {
        return order_user_id;
    }

    public void setOrder_user_id(int order_user_id) {
        this.order_user_id = order_user_id;
    }

    public int getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(int order_product_id) {
        this.order_product_id = order_product_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_image() {
        return order_image;
    }

    public void setOrder_image(String order_image) {
        this.order_image = order_image;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }
}