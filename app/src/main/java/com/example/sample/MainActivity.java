package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sample.client.HttpClient;
import com.example.sample.model.BorrowerResponse;
import com.example.sample.model.EmployeeResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    Listener listener;
    private AppBarConfiguration mAppBarConfiguration;
    private BorrowerResponse.Borrower borrower;
    private BorrowerResponse.Borrower collection;
    private NavController navController;
    private View fab;
    private ArrayList<BorrowerResponse.Borrower> borrowersList;

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.nav_dashboard)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        getWindow().setEnterTransition(new Explode());

        getWindow().setExitTransition(new Explode());

        setContentView(R.layout.activity_main);

        getBorrower();

    }

    private void startActivityContents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_bor_create, R.id.nav_bor_list, R.id.nav_col_create, R.id.nav_col_list)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(this);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void getEmployees() {
        HttpClient.getEmployees().enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                System.out.println("Responce :  " + response.code());
                if (response.body() != null) {
                    setEmployeeList(response.body().getEmployeesList());
                    startActivityContents();
                } else {
                    getEmployees();
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                getEmployees();
            }
        });
//        https://greatcoders.in/Financewebservices/employeelist.php
    }

    private void setEmployeeList(ArrayList<EmployeeResponse.Employee> employeeList) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (navController != null) navController.addOnDestinationChangedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getBorrower() {
        HttpClient.getBorrowers().enqueue(new Callback<BorrowerResponse>() {
            @Override
            public void onResponse(Call<BorrowerResponse> call, Response<BorrowerResponse> response) {
                System.out.println("Responce :  " + response.code());
                if (response.body() != null) {
                    setBorrowersList(response.body().getBorrowersList());
                    getEmployees();
                } else {
                    getBorrower();
                }
            }

            @Override
            public void onFailure(Call<BorrowerResponse> call, Throwable t) {
                System.out.println("Responce :  ");
                t.printStackTrace();
                getBorrower();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public ArrayList<BorrowerResponse.Borrower> getBorrowersList() {
        return borrowersList;
    }

    private void setBorrowersList(ArrayList<BorrowerResponse.Borrower> borrowersList) {
        this.borrowersList = borrowersList;
    }

    @Override
    public void onBackPressed() {
        if (listener != null && listener.onBackPressed())
            return;
        super.onBackPressed();
    }

    public interface Listener {
        default boolean onBackPressed() {
            return false;
        }
    }
}