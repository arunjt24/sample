package com.example.sample.ui.collection;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.util.Preference;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;

    private MaterialButton employee, status, collectionDate, paidDate, createCollection;
    private MainActivity activity;
    private int employeeAlertCheckedItem = 0, statusAlertCheckedItem = 0;
    private MaterialButton borrower;
    private int borrowerAlertCheckedItem = 0;
    private ProgressBar loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                new ViewModelProvider(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_collection, container, false);
        activity = (MainActivity) getActivity();
        employee = root.findViewById(R.id.employee);
        loader = root.findViewById(R.id.progress);
        employee.setOnClickListener(v -> {
            employee();
        });
        borrower = root.findViewById(R.id.barrower_name);
        borrower.setOnClickListener(v -> {
            borrower();
        });
        status = root.findViewById(R.id.loan_id);
        status.setOnClickListener(v -> {
            status();
        });
        collectionDate = root.findViewById(R.id.collection_date);
        collectionDate.setOnClickListener(v -> {
            collectionDate();
        });
        paidDate = root.findViewById(R.id.paid_date);
        paidDate.setOnClickListener(v -> {
            paidDate();
        });
        createCollection = root.findViewById(R.id.createCollection);
        createCollection.setOnClickListener(v -> {
            createCollection();
        });
        return root;
    }

    private void borrower() {
        AlertDialog.Builder employeeAlert = new AlertDialog.Builder(activity);
        employeeAlert.setCancelable(true);
        String[] branchArray = activity.getBorrowers();
        employeeAlert.setSingleChoiceItems(branchArray, borrowerAlertCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrowerAlertCheckedItem = which;
                borrower.setText(branchArray[which]);
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

    private void status() {
        AlertDialog.Builder statusAlert = new AlertDialog.Builder(activity);
        statusAlert.setCancelable(true);
        String[] branchArray = activity.getLoans();
        statusAlert.setSingleChoiceItems(branchArray, statusAlertCheckedItem, (dialog, which) -> {
            statusAlertCheckedItem = which;
            status.setText(branchArray[which]);
            dialog.cancel();
        });
        statusAlert.show();
    }

    private void collectionDate() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            collectionDate.setText(String.format(Locale.getDefault(), "%d/%d/%d", dayOfMonth, month, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void paidDate() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            paidDate.setText(String.format(Locale.getDefault(), "%d/%d/%d", dayOfMonth, month, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void createCollection() {
        loader.setVisibility(View.VISIBLE);
        createCollection.setVisibility(View.GONE);
        JsonObject jsonObject = new JsonObject();
        System.out.println("status.getText().toString() "+status.getText().toString());
        jsonObject.addProperty("Loanid", status.getText().toString());
        jsonObject.addProperty("Branchid", Preference.getBranchID());
        System.out.println("RESSSSSSL " + status.getText().toString() + Preference.getBranchID());
        HttpClient.createCollectionList(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("RESSSSSSL " + response.body());
                System.out.println("RESSSSSSL 1" + response.code());
                loader.setVisibility(View.GONE);
                createCollection.setVisibility(View.VISIBLE);
                redirect();
                Toast.makeText(getActivity(), "Collection Created!",
                        Toast.LENGTH_LONG).show();
                resetFields();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loader.setVisibility(View.GONE);
                createCollection.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Problem in Creating Collection!",
                        Toast.LENGTH_LONG).show();
                resetFields();
            }
        });
    }

    private void redirect() {
        activity.showCollectionList();
    }

    private void resetFields() {
        status.setText("Select Loan ID");
        statusAlertCheckedItem = 0;
        employee.setText("Select Employee");
        employeeAlertCheckedItem = 0;
    }
}