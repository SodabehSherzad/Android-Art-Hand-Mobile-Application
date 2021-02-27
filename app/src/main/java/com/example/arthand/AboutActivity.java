package com.example.arthand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {
    ImageView twitter, instagram, facebook, gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);
        gmail = findViewById(R.id.gmail);

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent. ACTION_VIEW);
                intent. setData(Uri. parse("https://mobile.twitter.com/CodeToInspire"));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent. ACTION_VIEW);
                intent. setData(Uri. parse("fb://facewebmodal/f?href=https://www.facebook.com/CodeToInspire"));
                startActivity(intent);
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent. ACTION_VIEW);
                intent. setData(Uri. parse("mailto:info@codetoinspire.org?subject=Makeup%20Feedback&body=Write%20feedback%20here..."));
                startActivity(intent);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent. ACTION_VIEW);
                intent. setData(Uri. parse("https://www.instagram.com/codetoinspire/?hl=en"));
                startActivity(intent);

            }
        });
    }

    public void goBack(View view){
        super.onBackPressed();
    }


}
