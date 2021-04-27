package com.example.sample.ui.loan;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.ui.borrower.CreateViewModel;
import com.example.sample.util.Preference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
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
        createloan.setOnClickListener(n->{
            createloan();
        });
        employee.setOnClickListener(v -> {
            employee();
        });
        loan_type.setOnClickListener(v->{
            loantype();
        });
        barrower_name.setOnClickListener(n->{
            borrower();
        });
        loan_status.setOnClickListener(n->{
            loanstaus();
        });

        return root;
    }

    private void loanstaus() {
        ArrayList<String> loan_status = new ArrayList<>();
        loan_status.add("Open");
        loan_status.add("Closed");
        AlertDialog.Builder loanstatusAlert = new AlertDialog.Builder(activity);
        loanstatusAlert.setCancelable(true);
        String[] branchArray = loan_status.toArray(new String[0]);
        loanstatusAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                loan_type.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        loanstatusAlert.show();
    }

    private void loantype() {
        ArrayList<String> loan_type = new ArrayList<>();
        loan_type.add("Daily");
        loan_type.add("Monthly");
        loan_type.add("Weekly");
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = loan_type.toArray(new String[0]);
        employeeAlert.setSingleChoiceItems(branchArray, employeeAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employeeAlertCheckedItem = which;
                loan_status.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }

    private void employee() {
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = activity.getEmployee();
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
                employee.setText(branchArray[which]);
                dialog.cancel();
            }
        });
        employeeAlert.show();
    }

    private void createloan() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("principal_amount", principal_amount.getText().toString());
        jsonObject.addProperty("service_charge", service_charge.getText().toString());
        HttpClient.createLoan(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("RESSSSSS " + response.body());
                //resetFields();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //resetFields();
            }
        });
    }
}