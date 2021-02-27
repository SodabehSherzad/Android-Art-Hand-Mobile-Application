package com.example.arthand.ui.profile;

public class ProfileViewModel {
    int userId;
    String fname, lname, username, phone, country, address, gmail, password, type, gender, birth_year;

    public ProfileViewModel(int userId, String fname, String lname, String username, String phone, String country, String address, String gmail, String password, String type, String gender, String birth_year) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.phone = phone;
        this.country = country;
        this.address = address;
        this.gmail = gmail;
        this.password = password;
        this.type = type;
        this.gender = gender;
        this.birth_year =birth_year;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}