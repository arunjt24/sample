package com.example.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.client.HttpClient;
import com.example.sample.model.BranchResponse;
import com.example.sample.model.Login;
import com.example.sample.model.User;
import com.example.sample.util.Preference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;
import static java.net.HttpURLConnection.HTTP_OK;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout nameInput;
    private TextInputLayout passwordInput;
    private TextInputEditText name;
    private TextInputEditText password;
    private int checkedItem = 0;
    private MaterialButton branchButton;
    private String[] branchArray = new String[]{};
    private TextView loginMessage;
    private List<BranchResponse.Branch> branchList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Preference(this).init();

        if (Preference.isUserLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            return;
        }

        initValues();
        initViews();
    }

    private void initValues() {
        getBranches();
    }

    private void initViews() {

        branchButton = findViewById(R.id.selectBranch);
        loginMessage = findViewById(R.id.loginMessage);

        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);

        name = findViewById(R.id.email);
        password = findViewById(R.id.password);

        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                isValidName(getName());
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                isValidPassword(getPassword());
            }
        });
    }

    private void getBranches() {
        HttpClient.getBranches().enqueue(new Callback<BranchResponse>() {

            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                if (response.code() == HTTP_OK) {
                    branchList = response.body().getBranchList();
                } else {
                    getBranches();
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                getBranches();
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
        if ((!TextUtils.isEmpty(name)/* && Patterns.EMAIL_ADDRESS.matcher(name).matches()*/)) {
            nameInput.setError(null);
            return true;
        } else {
            nameInput.setError("Enter the valid name...!");
            return false;
        }
    }

    private boolean isValidPassword(String password) {
        if (!TextUtils.isEmpty(password)) {
            passwordInput.setError(null);
            return true;
        } else {
            passwordInput.setError("Enter the password!");
            return false;
        }
    }

    public void doLogin(View view) {
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

        HttpClient.login(login).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("response.body() " + response.body());
                if (response.code() == HTTP_OK) {
                    User user = response.body();
                    if (user != null) {
                        if (user.getSuccess() == 1) {
                            Preference.setUserLoggedIn(user.getEmployeeId(),user.getBranchId());
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            System.out.println(user.getMessage());
                            loginFailed(user.getMessage());
                        }
                    } else {
                        System.out.println("Failed to login! 1");
                        loginFailed("Failed to login!");
                    }
                } else {
                    System.out.println("Failed to login! 2");
                    loginFailed("Failed to login!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed to login! 3");
                loginFailed("Failed to login!");
            }

            private void loginFailed(String message) {
                loginMessage.setText(message);
                loginMessage.setVisibility(VISIBLE);
                name.requestFocus();
            }
        });
    }

    public void showBranches(View view) {
        List<String> tempList = new ArrayList<>();
        for (BranchResponse.Branch branch : branchList) {
            tempList.add(branch.getBranchName());
        }
        branchArray = tempList.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(branchArray, checkedItem, (dialog, which) -> {
            checkedItem = which;
            branchButton.setText(branchArray[which]);
            dialog.cancel();
        });
        builder.show();
    }
}