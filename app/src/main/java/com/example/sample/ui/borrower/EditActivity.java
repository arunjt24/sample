package com.example.sample.ui.borrower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.model.Borrower;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private String borrowersId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_edit);
        Borrower borrower = new Gson().fromJson(getIntent().getExtras().getString("borrower"),Borrower.class);

        final MaterialButton update = findViewById(R.id.updateBorrower);

        TextInputEditText firstName = findViewById(R.id.first_name);
        TextInputEditText fatherName = findViewById(R.id.father_name);
        TextInputEditText eMail = findViewById(R.id.e_mail);
        TextInputEditText mobileNumber = findViewById(R.id.mobile_number);
        TextInputEditText address = findViewById(R.id.address);
        TextInputEditText occupation = findViewById(R.id.occupation);
        TextInputEditText monthlyIncome = findViewById(R.id.monthly_income);
        TextInputEditText kycProof = findViewById(R.id.proof_no);
        TextInputEditText referenceName = findViewById(R.id.reference_name);
        TextInputEditText referenceMobile = findViewById(R.id.reference_mobile);
        borrowersId = borrower.getBorrowersId();
        firstName.setText(borrower.getFirstName());
        fatherName.setText(borrower.getFatherName());
        eMail.setText(borrower.getEmail());
        mobileNumber.setText(borrower.getMobile());
        address.setText(borrower.getAddress());
        occupation.setText(borrower.getOccupation());
        monthlyIncome.setText(borrower.getMonthlyIncome());
        kycProof.setText(borrower.getProof());
        referenceName.setText(borrower.getReferenceName());
        referenceMobile.setText(borrower.getReferencMobile());

        if (firstName.getText() != null &&
                fatherName.getText() != null &&
                eMail.getText() != null &&
                mobileNumber.getText() != null &&
                address.getText() != null &&
                occupation.getText() != null &&
                monthlyIncome.getText() != null &&
                kycProof.getText() != null &&
                referenceName.getText() != null &&
                referenceMobile.getText() != null ) {

            update.setOnClickListener(v -> {
                Borrower editBorrower = new Borrower();
                editBorrower.setFirstName(firstName.getText().toString());
                editBorrower.setFatherName(fatherName.getText().toString());
                editBorrower.setEmail(eMail.getText().toString());
                editBorrower.setMobile(mobileNumber.getText().toString());
                editBorrower.setAddress(address.getText().toString());
                editBorrower.setOccupation(occupation.getText().toString());
                editBorrower.setMonthlyIncome(monthlyIncome.getText().toString());
                editBorrower.setProof(kycProof.getText().toString());
                editBorrower.setReferenceName(referenceName.getText().toString());
                editBorrower.setReferencMobile(referenceMobile.getText().toString());
                editBorrower.setBorrowersId(borrowersId);
                System.out.println("Borrower Model : "+editBorrower.toString());
                HttpClient.editBorrower(borrower).enqueue(new Callback<Borrower>() {
                    @Override
                    public void onResponse(Call<Borrower> call, Response<Borrower> response) {
                        System.out.println("response.body() "+response.code());
                        System.out.println("response.body() "+response.body().toString());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Borrower> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            });
        }

    }
}