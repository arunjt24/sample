package com.example.sample.ui.loan;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.model.BorrowerResponse;
import com.example.sample.ui.borrower.CreateViewModel;
import com.example.sample.util.Preference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoanFragment extends Fragment {


    private MainActivity activity;
    private CreateViewModel createViewModel;
    private MaterialButton employee, loan_type, barrower_name, loan_status, createloan;
    private TextInputEditText principal_amount,service_charge,initiated_amount,interest,estimation_month;
    private int employeeAlertCheckedItem = 0;
    private String[] toArray = new String[]{};
    private int borrowerAlertCheckedItem = 0;
    private ProgressBar loader;
    private String barroower_id = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        View root = inflater.inflate(R.layout.fragment_loan, container, false);
        activity = (MainActivity) getActivity();

        employee = root.findViewById(R.id.employee);
        loan_type = root.findViewById(R.id.loan_type);
        barrower_name = root.findViewById(R.id.barrower_name);
        principal_amount = root.findViewById(R.id.principal_amount);
        service_charge = root.findViewById(R.id.service_charge);
        initiated_amount = root.findViewById(R.id.initiated_amount);
        interest  = root.findViewById(R.id.interest);
        estimation_month = root.findViewById(R.id.estimation_month);
        loan_status = root.findViewById(R.id.loan_status);
        createloan = root.findViewById(R.id.createloan);
        loader = root.findViewById(R.id.progress);

        getBorrower();

        createloan.setOnClickListener(n->{
            createloan();
        });
        employee.setOnClickListener(v -> {
            employee();
        });

        barrower_name.setOnClickListener(n->{
            showBorrower();
        });
        loan_type.setOnClickListener(v->{
            loantype();
        });
        loan_status.setOnClickListener(n->{
            loanstaus();
        });

        return root;
    }

    private void showBorrower() {
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = activity.getLoanBorrowers();
        employeeAlert.setSingleChoiceItems(branchArray, borrowerAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrowerAlertCheckedItem = which;
                String[] item = branchArray[which].split(":");
                barrower_name.setText(item[0]);
                barroower_id = item[1];
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }

    private void getBorrower() {
        HttpClient.getBorrowers().enqueue(new Callback<BorrowerResponse>() {
            @Override
            public void onResponse(Call<BorrowerResponse> call, Response<BorrowerResponse> response) {
                System.out.println("Loan Responce :  " + response.code());

                if (response.body() != null) {
                    ArrayList<String> sample = new ArrayList<>();
                    for (BorrowerResponse.Borrower test : response.body().getBorrowersList()) {
                        sample.add(test.getBorrowersId());
                    }
                    setBorrowerIds(sample.toArray(new String[0]));
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

    private void setBorrowerIds(String[] toArray) {
        this.toArray = toArray;
    }

    private void loanstaus() {
        ArrayList<String> loan_status_l = new ArrayList<>();
        loan_status_l.add("Open");
        loan_status_l.add("Closed");
        AlertDialog.Builder loanstatusAlert = new AlertDialog.Builder(activity);
        loanstatusAlert.setCancelable(true);
        String[] branchArray = loan_status_l.toArray(new String[0]);
        loanstatusAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                loan_status.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        loanstatusAlert.show();
    }

    private void loantype() {
        ArrayList<String> loan_type_l = new ArrayList<>();
        loan_type_l.add("Daily");
        loan_type_l.add("Monthly");
        loan_type_l.add("Weekly");
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = loan_type_l.toArray(new String[0]);
        employeeAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                loan_type.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }

    private void employee() {
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = activity.getEmployee();
        System.out.println("branchArray "+branchArray.toString());
        employeeAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                employee.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }

    private void borrower() {
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = activity.getEmployee();
        employeeAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                barrower_name.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }
    private void resetFields(){
        principal_amount.setText(null);
        service_charge.setText(null);
        initiated_amount.setText(null);
        interest.setText(null);
        estimation_month.setText(null);
    }
    private void createloan() {
        createloan.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new Gson().fromJson("{\"Borrowrsid\":"+barroower_id+",\"Employeeid\":"+Preference.getEmployeeID()+",\"Branchid\":"+Preference.getBranchID()+",\"Collectiontype\":\"1\",\"principalamount\":"+principal_amount.getText().toString()+"}",JsonObject.class);
        jsonObject.addProperty("service_charge", service_charge.getText().toString());


        HttpClient.createLoan(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("responsetynhnjhnh "+response.body());
                System.out.println("responsetynhnjhnh "+response.code());
                createloan.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                redirect();
                Toast.makeText(getActivity(), "Loan Created ..!",
                        Toast.LENGTH_LONG).show();
                System.out.println("RESSSSSS " + response.body());
                resetFields();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                System.out.println("RESSSSSS " +t);
                createloan.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Problem in Loan Creation ..!",
                        Toast.LENGTH_LONG).show();
                resetFields();
            }
        });
    }

    private void redirect() {
        activity.showLoanList();
    }
}