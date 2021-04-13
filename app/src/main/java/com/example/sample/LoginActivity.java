package com.example.sample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.sample.client.HttpClient;
import com.example.sample.model.Login;
import com.example.sample.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;
import static java.net.HttpURLConnection.HTTP_OK;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout nameInput;
    private TextInputLayout passwordInput;
    private TextInputEditText name;
    private TextInputEditText password;
    private int checkedItem = 0;
    private MaterialButton branchButton;
    private String[] branchArray;
    private TextView loginMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initValues();
        initViews();
    }

    private void initValues() {
        branchArray = getResources().getStringArray(R.array.branch_s);
    }

    private void initViews() {

        branchButton = findViewById(R.id.selectBranch);
        loginMessage = findViewById(R.id.loginMessage);
        branchButton.setText(branchArray[0]);

        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);

        name = findViewById(R.id.email);
        password = findViewById(R.id.password);

        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                isValidName(getName());
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                isValidPassword(getPassword());
            }
        });
    }

    public String getName() {
        return name.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    private boolean isValidName(String name) {
        if ((!TextUtils.isEmpty(name)/* && Patterns.EMAIL_ADDRESS.matcher(name).matches()*/)){
            nameInput.setError(null);
            return true;
        } else {
            nameInput.setError("Enter the valid name...!");
            return false;
        }
    }

    private boolean isValidPassword(String password) {
        if (!TextUtils.isEmpty(password)){
            passwordInput.setError(null);
            return true;
        } else {
            passwordInput.setError("Enter the password!");
            return false;
        }
    }

    public void goMain(View view) {
        if (!isValidName(getName())) {
            name.callOnClick();
            name.requestFocus();
            return;
        }

        if (!isValidPassword(getPassword())) {
            password.callOnClick();
            password.requestFocus();
            return;
        }
        Login login = new Login();
        System.out.println(getName());
        System.out.println(getPassword());
        login.setUserName(getName());
        login.setUserPassword(getPassword());
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
//        HttpClient.login(login).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.code() == HTTP_OK) {
//                    User user = response.body();
//                    if (user != null) {
//                        if (user.getSuccess() == 1) {
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                            finish();
//                        } else {
//                            System.out.println(user.getMessage());
//                            loginFailed(user.getMessage());
//                        }
//                    } else {
//                        System.out.println("Failed to login! 1");
//                        loginFailed("Failed to login!");
//                    }
//                } else {
//                    System.out.println("Failed to login! 2");
//                    loginFailed("Failed to login!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                System.out.println("Failed to login! 3");
//                loginFailed("Failed to login!");
//            }
//
//            private void loginFailed(String message) {
//                loginMessage.setText(message);
//                loginMessage.setVisibility(VISIBLE);
//                name.requestFocus();
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Branch "+parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showBranches(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(branchArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem = which;
                branchButton.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        builder.show();
    }
}