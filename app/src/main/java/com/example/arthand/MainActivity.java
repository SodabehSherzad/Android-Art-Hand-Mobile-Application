package com.example.arthand;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.arthand.ui.user.UserItemActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static DatabaseHelper databaseHelper;
    NavigationView navigationView;
    DrawerLayout drawer;
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);
         navView = findViewById(R.id.bottom_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_favorite, R.id.navigation_cart, R.id.navigation_user)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_product:
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println("id "+id);
        if (id == R.id.nav_home){
            navView.setSelectedItemId(R.id.navigation_home);
        } else if (id == R.id.nav_user) {
            navView.setSelectedItemId(R.id.navigation_user);
        } else if (id == R.id.nav_favorite) {
            navView.setSelectedItemId(R.id.navigation_favorite);
        } else if (id == R.id.nav_cart) {
            navView.setSelectedItemId(R.id.navigation_cart);
        } else if (id == R.id.nav_profiles) {
            navView.setSelectedItemId(R.id.navigation_profile);
        } else if (id == R.id.nav_buy) {
            Intent aboutIntent = new Intent(MainActivity.this,  OrderedActivity.class);
            startActivity(aboutIntent);
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(MainActivity.this,  AboutActivity.class);
            startActivity(aboutIntent);
        }
        else if (id == R.id.nav_user_item) {
            Intent userItem = new Intent(MainActivity.this,  UserItemActivity.class);
            startActivity(userItem);
        }
//        else if (id == R.id.nav_share) {
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            String shareBody = "Here is the share content body";
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}