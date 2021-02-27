package com.example.arthand;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.arthand.Model;
import com.example.arthand.ui.cart.CartViewModel;
import com.example.arthand.ui.favorites.FavoritesViewModel;
import com.example.arthand.ui.home.HomeViewModel;
import com.example.arthand.ui.profile.ProfileViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "onlineshopping.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    static SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public List<HomeViewModel> getImage() {
        List<HomeViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        // group by sub_category_id
        String query = "select * from images inner join product on product.id == product_id order by id desc";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new HomeViewModel( c.getInt(0), c.getString(2) ));
        }
        return arrayList;
    }

    public List<HomeViewModel> getCategory() {
        List<HomeViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM Category";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new HomeViewModel(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return arrayList;
    }

    public List<Model> getProduct() {
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        // group by sub_category_id
        String query = "Select * from product inner join images on product.id == images.product_id order by timestamp desc";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new Model( c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getInt(5), c.getDouble(6), c.getDouble(7), c.getString(8), c.getString(9), c.getDouble(10), c.getString(14) ));
        }
        return arrayList;
    }

    public List<Model> getSubCategory(int condition) {
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT distinct sub_category.id, sub_category.name, sub_category.image FROM sub_category inner join product on sub_category.id == product.sub_category_id WHERE category_id = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{Integer.toString(condition)});

        while (c.moveToNext()) {
            arrayList.add(new Model(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return arrayList;
    }

    public List<Model> getProduct(int subCategory) {
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "Select * from product inner join images on product.id == images.product_id where sub_category_id = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{Integer.toString(subCategory)});

        while (c.moveToNext()) {
            arrayList.add(new Model( c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getInt(5), c.getDouble(6), c.getDouble(7), c.getString(8), c.getString(9), c.getDouble(10), c.getString(14) ));
        }
        return arrayList;
    }

    public void insertImage(int productId, String image){
        mDataBase = this.getWritableDatabase();
//        Uri uri = imageList.get(0);
        String query = "INSERT INTO images(product_id, image) VALUES(?, ?)";
//        for (int i = 0; i < imageList.size(); i++){
//            Uri uri = imageList.get(i);
//            mDataBase.execSQL(query, new String[]{Integer.toString(productId), uri.toString()});
//        }
        mDataBase.execSQL(query, new String[]{Integer.toString(productId), image});
    }

    public int getLastProductId(){
        mDataBase = this.getWritableDatabase();
        String query = "SELECT id FROM product ORDER BY id DESC LIMIT 1";
        Cursor c = mDataBase.rawQuery(query, null);
        c.moveToLast();
        return c.getInt(0);
    }

    public List<String> listOfCategory(){
        List<String> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM category";
        Cursor c = mDataBase.rawQuery(query, null);
        while(c.moveToNext()){
            arrayList.add(c.getString(1));
        }

        return arrayList;
    }

    public List<String> listOfSubcategory(String category){
        List<String> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "select * from category inner join sub_category on category.id == sub_category.category_id and category.name = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{category});
        while(c.moveToNext()){
            arrayList.add(c.getString(4));
        }
        return arrayList;
    }

    public int subcategoryId(String subCategory){
        mDataBase = this.getWritableDatabase();
        String query = "select * from sub_category where name = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{subCategory});
        c.moveToLast();
        return c.getInt(0);
    }

    public void insertProduct(String image, int subcategoryId, int userId, String productName, String productDetail, String productNumber,
                              String productSize, String productWeight, String productDimension, String productColor, String productPrice){
        mDataBase = this.getWritableDatabase();
        String query = "INSERT INTO product(sub_category_id, user_id, name, detail, number, size, weight, dimension, color, price) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        mDataBase.execSQL(query, new String[]{Integer.toString(subcategoryId), Integer.toString(userId), productName, productDetail,
                productNumber, productSize, productWeight, productDimension, productColor, productPrice});

        int product_id = getLastProductId();
        insertImage(product_id, image);

    }

    public void updateProduct(String image, int product_id, int getSubcategory_id, String name, String detail, String number, String size, String weight, String dimension, String color, String price) {
        mDataBase = this.getWritableDatabase();
        String query = "UPDATE product SET sub_category_id = ?, name = ?, detail = ?, number = ?, size = ?, weight = ?, dimension = ?, color = ?, price = ? WHERE id = ?";
        mDataBase.execSQL(query, new String[]{Integer.toString(getSubcategory_id), name, detail,
                number, size, weight, dimension, color, price, Integer.toString(product_id)});

        updateImage(product_id, image);
    }

    private void updateImage(int product_id, String image) {
        mDataBase = this.getWritableDatabase();
        String query = "UPDATE images SET image = ? WHERE product_id = ?";
        mDataBase.execSQL(query, new String[]{image, Integer.toString(product_id)});
    }

    public List<ProfileViewModel> getUser(){
        List<ProfileViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM users";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new ProfileViewModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11)));
        }
        return arrayList;
    }

    public void insertUser(String fName, String lName, String username, String phone, String country, String address, String gmail, String password, String type){
        mDataBase = this.getWritableDatabase();
        String query = "INSERT INTO users(fname, lname, username, phone, country, address, gmail, password, type) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        mDataBase.execSQL(query, new String[]{fName, lName, username, phone, country, address, gmail, password, type});
    }

    public List<Model> getFavorite(){
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "select * from `like` inner join images on product.id = images.product_id inner join product on product.id = `like`.product_id inner join users on users.id = `like`.user_id";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new Model( c.getInt(6), c.getInt(7), c.getInt(8), c.getString(9), c.getString(10), c.getInt(11), c.getDouble(12), c.getDouble(13), c.getString(14), c.getString(15), c.getDouble(16), c.getString(5) ));
        }
        return arrayList;
    }

    public void setFavorite(int user_id, int product_id){
        mDataBase = this.getWritableDatabase();
        String query = "INSERT INTO `like`(user_id, product_id) VALUES(?, ?)";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), Integer.toString(product_id)});
    }

    public void unsetFavorite(int user_id, int product_id){
        mDataBase = this.getWritableDatabase();
        String query = "DELETE FROM `like` WHERE user_id = ? AND product_id = ?";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), Integer.toString(product_id)});
    }

    public boolean checkFavorite(int u_id, int p_id){
        List<FavoritesViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM `like` WHERE user_id == ? AND product_id = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{Integer.toString(u_id), Integer.toString(p_id)});

        while (c.moveToNext()) {
            arrayList.add(new FavoritesViewModel( c.getInt(0), c.getInt(1), c.getInt(2) ));
        }
        return (arrayList.size()== 0) ? false : true;
    }

    public List<CartViewModel> getCart(){
        List<CartViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "select * from `cart` inner join images on product.id = images.product_id inner join product on product.id = `cart`.product_id inner join users on users.id = `cart`.user_id";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new CartViewModel(
                    c.getInt(0), c.getInt(1), c.getInt(2), c.getString(9), c.getString(5), c.getString(15), c.getString(10), c.getString(14), c.getInt(11), c.getDouble(12), c.getDouble(13), c.getDouble(16)));
        }
        return arrayList;
    }

    public void setCart(int user_id, int product_id){
        mDataBase = this.getWritableDatabase();
        String query = "INSERT INTO cart(user_id, product_id) VALUES(?, ?)";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), Integer.toString(product_id)});
    }

    public void unsetCart(int user_id, int product_id){
        mDataBase = this.getWritableDatabase();
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), Integer.toString(product_id)});
    }

    public boolean checkCart(int u_id, int p_id){
        List<CartViewModel> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM cart WHERE user_id == ? AND product_id = ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{Integer.toString(u_id), Integer.toString(p_id)});

        while (c.moveToNext()) {
            arrayList.add(new CartViewModel( c.getInt(0), c.getInt(1), c.getInt(2) ));
        }
        return (arrayList.size()== 0) ? false : true;
    }

    public void setOrder(int user_id, String order_date, int product_id, String status){
        mDataBase = this.getWritableDatabase();
        String query = "INSERT INTO `order`(user_id, order_date, product_id, status) VALUES(?, ?, ?, ?)";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), order_date, Integer.toString(product_id), status});
    }

    public List<Model> getOrder(){
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "select * from `order` inner join images on product.id = images.product_id inner join product on product.id = `order`.product_id inner join users on users.id = `order`.user_id";
        Cursor c = mDataBase.rawQuery(query, null);

        while (c.moveToNext()) {
            arrayList.add(new Model(
            c.getInt(1), c.getInt(2), c.getString(3), c.getString(4),
                    c.getString(11), c.getString(12), c.getInt(13), c.getDouble(14), c.getDouble(15),
            c.getString(16), c.getString(17), c.getDouble(18), c.getString(7)
            ));
        }
        return arrayList;
    }

    public void unsetOrder(int user_id, int product_id) {
        mDataBase = this.getWritableDatabase();
        String query = "DELETE FROM `order` WHERE user_id = ? AND product_id = ?";
        mDataBase.execSQL(query, new String[]{Integer.toString(user_id), Integer.toString(product_id)});
    }

    public void updateOrder(int user_id, int product_id, String newStatus){
        mDataBase = this.getWritableDatabase();
        String query = "UPDATE `order` SET status = ? WHERE user_id = ? AND product_id = ?";
        mDataBase.execSQL(query, new String[]{newStatus, Integer.toString(user_id), Integer.toString(product_id)});
    }

    public List<Model> getStatusOrder(String status){
        List<Model> arrayList = new ArrayList<>();
        mDataBase = this.getWritableDatabase();
        String query = "select * from `order` inner join images on product.id = images.product_id inner join product on product.id = `order`.product_id inner join users on users.id = `order`.user_id where status == ?";
        Cursor c = mDataBase.rawQuery(query, new String[]{status});

        while (c.moveToNext()) {
            arrayList.add(new Model(
                    c.getInt(1), c.getInt(2), c.getString(3), c.getString(4),
                    c.getString(11), c.getString(12), c.getInt(13), c.getDouble(14), c.getDouble(15),
                    c.getString(16), c.getString(17), c.getDouble(18), c.getString(7)
            ));
        }
        return arrayList;
    }


    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

}
