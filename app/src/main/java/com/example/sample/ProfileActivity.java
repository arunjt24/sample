package com.example.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button username,password;
    private LinearLayout f_username_update,f_password_update,update_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        username = findViewById(R.id.update_user_name);
        password = findViewById(R.id.update_user_password);
        f_password_update = findViewById(R.id.f_password_update);
        f_username_update = findViewById(R.id.f_username_update);
        update_user = findViewById(R.id.update_user);
        username.setText("Update UserName");
        password.setText("Update Password");
        username.setOnClickListener(n->{
            update_user.setVisibility(View.GONE);
            f_username_update.setVisibility(View.VISIBLE);
        });
        password.setOnClickListener(n->{
            update_user.setVisibility(View.GONE);
            f_password_update.setVisibility(View.VISIBLE);
        });
    }
}