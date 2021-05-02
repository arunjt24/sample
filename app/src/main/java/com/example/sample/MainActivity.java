package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sample.client.HttpClient;
import com.example.sample.model.BorrowerResponse;
import com.example.sample.model.CollectionsResponse;
import com.example.sample.model.EmployeeResponse;
import com.example.sample.model.LoanResponse;
import com.example.sample.ui.collection.ListViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener, NavigationView.OnNavigationItemSelectedListener {

    Listener listener;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private View fab;
    private ArrayList<BorrowerResponse.Borrower> borrowersList  = new ArrayList<>();
    private ArrayList<LoanResponse.Loan> loanList  = new ArrayList<>();
    private ArrayList<EmployeeResponse.Employee> employeeList  = new ArrayList<>();
    private ArrayList<CollectionsResponse.Collection> collectionLists = new ArrayList<>();
    private ListViewModel collectionListModel;
    private com.example.sample.ui.borrower.ListViewModel borrowerListModel;
    private com.example.sample.ui.loan.ListViewModel loanListModel;
    private NavigationView navigationView;
    private SearchView searchview;

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

        getEmployees();

        getBorrower();

        getLoanList();

        startActivityContents();

    }

    private void startActivityContents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        searchview = findViewById(R.id.search_view);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                searchview.onActionViewCollapsed();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (borrowerListModel != null) {
                    if (newText.equals("")) {
                        borrowerListModel.updateData(borrowersList);
                        return false;
                    }
                    ArrayList<BorrowerResponse.Borrower> filteredList = new ArrayList<>();
                    for (BorrowerResponse.Borrower borrower: borrowersList) {
                        if (borrower.getFirstName().contains(newText)) {
                            filteredList.add(borrower);
                        }
                    }
                    borrowerListModel.updateData(filteredList);
                }
                if (collectionListModel != null) {
                    if (newText.equals("")) {
                        collectionListModel.updateData(collectionLists);
                        return false;
                    }
                    ArrayList<CollectionsResponse.Collection> filteredList = new ArrayList<>();
                    for (CollectionsResponse.Collection collection: collectionLists) {
                        if (collection.getEmployeeid().contains(newText)) {
                            filteredList.add(collection);
                        }
                    }
                    collectionListModel.updateData(filteredList);
                }
                if (loanListModel != null){
                    if (newText.equals("")) {
                        loanListModel.updateData(loanList);
                        return false;
                    }
                    ArrayList<LoanResponse.Loan> filteredList = new ArrayList<>();
                    for (LoanResponse.Loan loan: loanList) {
                        if (loan.getEmployeeid().contains(newText)) {
                            filteredList.add(loan);
                        }
                    }
                    loanListModel.updateData(filteredList);
                }
                return false;
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_bor_create, R.id.nav_bor_list, R.id.nav_col_create, R.id.nav_col_list,R.id.nav_loan_create,R.id.nav_loan_list,R.id.nav_expenses_create,R.id.nav_expenses_list,R.id.nav_report_list)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void getEmployees() {
        HttpClient.getEmployees().enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                System.out.println("Responce :  " + response.body().toString());
                if (response.body() != null) {
                    setEmployeeList(response.body().getEmployeesList());
                } else {
                    getEmployees();
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                getEmployees();
            }
        });
    }

    public void getLoanList() {
        HttpClient.getLoanList().enqueue(new Callback<LoanResponse>() {
            @Override
            public void onResponse(Call<LoanResponse> call, Response<LoanResponse> response) {
                System.out.println("Responce loan:  " + response.body().toString());
                if (response.body() != null) {
                    setLoanList(response.body().getLoantlist());
                } else {
                    getLoanList();
                }
            }

            @Override
            public void onFailure(Call<LoanResponse> call, Throwable t) {
                getLoanList();
            }
        });
    }

    private void setLoanList(ArrayList<LoanResponse.Loan> collectionType) {
        if (loanListModel != null)
            loanListModel.updateData(collectionType);
        this.loanList = collectionType;
    }

    private void setEmployeeList(ArrayList<EmployeeResponse.Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String[] getLoans() {
        ArrayList<String> types = new ArrayList<>();
        for (LoanResponse.Loan type : loanList) {
            types.add(type.getLoanid());
        }

        return types.toArray(new String[0]);
    }

    public String[] getEmployee() {
        ArrayList<String> employees = new ArrayList<>();
        for (EmployeeResponse.Employee type : employeeList) {
            employees.add(type.getUsername());
        }
        System.out.println("employeeList"+employeeList.toString());
        return employees.toArray(new String[0]);
    }

    public ArrayList<CollectionsResponse.Collection> getCollectionLists() {
        return collectionLists;
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

    public void getCollection() {
        HttpClient.getCollectionList().enqueue(new Callback<CollectionsResponse>() {
            @Override
            public void onResponse(Call<CollectionsResponse> call, Response<CollectionsResponse> response) {
                if (response.body() != null) {
                    setCollectionList(response.body().getCollectionList());
                } else {
                    getCollection();
                }
            }

            @Override
            public void onFailure(Call<CollectionsResponse> call, Throwable t) {
                getCollection();
            }
        });
    }

    public ArrayList<BorrowerResponse.Borrower> getBorrowersList() {
        return borrowersList;
    }

    private void setBorrowersList(ArrayList<BorrowerResponse.Borrower> borrowersList) {
        if (borrowerListModel != null)
            borrowerListModel.updateData(borrowersList);
        this.borrowersList = borrowersList;
    }

    private void setCollectionList(ArrayList<CollectionsResponse.Collection> collectionLists) {
        if (collectionListModel != null)
            collectionListModel.updateData(collectionLists);
        this.collectionLists = collectionLists;
    }

    @Override
    public void onBackPressed() {
        if (listener != null && listener.onBackPressed())
            return;
        super.onBackPressed();
    }

    public void setCollectionModel(ListViewModel listViewModel) {
        this.collectionListModel = listViewModel;
    }

    public void setBorrowerModel(com.example.sample.ui.borrower.ListViewModel listViewModel) {
        this.borrowerListModel = listViewModel;
    }

    public void setLoanModel(com.example.sample.ui.loan.ListViewModel listViewModel) {
        this.loanListModel = listViewModel;
    }

    public String[] getBorrowers() {
        ArrayList<String> types = new ArrayList<>();
        for (BorrowerResponse.Borrower type : borrowersList) {
            types.add(type.getFirstName());
        }

        return types.toArray(new String[0]);
    }

    public String[] getLoanBorrowers() {
        ArrayList<String> types = new ArrayList<>();
        for (BorrowerResponse.Borrower type : borrowersList) {
            //types.add(type.getFirstName());
            types.add(type.getFirstName()+":"+type.getBorrowersId());
        }

        return types.toArray(new String[0]);
    }

    public void showLoanList() {
        System.out.println("navigationView.getMenu() " + navigationView.getMenu().size());
        navController.navigate(R.id.nav_loan_list);
//        navigationView.getMenu().findItem(R.id.nav_loan_list).setChecked(true);
    }
    public void showCollectionList() {
        System.out.println("navigationView.getMenu() 1 " + navigationView.getMenu().size());
        navController.navigate(R.id.nav_col_list);
//        navigationView.getMenu().findItem(R.id.nav_loan_list).setChecked(true);
    }
    public void showBarrowerList() {
        navController.navigate(R.id.nav_bor_list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public interface Listener {
        default boolean onBackPressed() {
            return false;
        }
    }
}