package com.example.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.client.HttpClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private Button username, password, User_update, Password_update;
    private LinearLayout f_username_update, f_password_update, update_user;
    private TextInputEditText old_username, new_username, conform_password, old_password, new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        username = findViewById(R.id.update_user_name);
        password = findViewById(R.id.update_user_password);
        f_password_update = findViewById(R.id.f_password_update);
        f_username_update = findViewById(R.id.f_username_update);
        User_update = findViewById(R.id.update_user_name_button);
        Password_update = findViewById(R.id.update_password_button);
        update_user = findViewById(R.id.update_user);
        old_username = findViewById(R.id.old_username);
        new_username = findViewById(R.id.new_username);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        conform_password = findViewById(R.id.conform_password);
        username.setText("Update UserName");
        password.setText("Update Password");
        username.setOnClickListener(n -> {
            update_user.setVisibility(View.GONE);
            f_username_update.setVisibility(View.VISIBLE);
        });
        password.setOnClickListener(n -> {
            update_user.setVisibility(View.GONE);
            f_password_update.setVisibility(View.VISIBLE);
        });
        Password_update.setOnClickListener(n -> {
            String oldpassword = old_password.getText().toString();
            String newpassword = new_password.getText().toString();
            String conformpassword = conform_password.getText().toString();
            JSONObject data = new JSONObject();
            try {
                data.put("Employeeid", "1");
                data.put("OldPassword", oldpassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (newpassword == conformpassword)
                HttpClient.update_pass(data);
        });
        update_user.setOnClickListener(n -> {
            JSONObject data = new JSONObject();
            try {
                data.put("Username", new_username.getText().toString());
                data.put("Employeeid", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpClient.update_user(data);
        });

    }

    @Override
    public void onBackPressed() {
        if (update_user.getVisibility() == View.VISIBLE)
            super.onBackPressed();
        else {
            f_username_update.setVisibility(View.GONE);
            f_password_update.setVisibility(View.GONE);
            update_user.setVisibility(View.VISIBLE);
        }
    }
}