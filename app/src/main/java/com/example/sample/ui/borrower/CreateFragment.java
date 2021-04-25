package com.example.sample.ui.borrower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.model.BorrowerResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        View root = inflater.inflate(R.layout.fragment_new_borrowers, container, false);

        TextInputEditText firstName = root.findViewById(R.id.first_name);
        TextInputEditText fatherName = root.findViewById(R.id.father_name);
        TextInputEditText eMail = root.findViewById(R.id.e_mail);
        TextInputEditText mobileNumber = root.findViewById(R.id.mobile_number);
        TextInputEditText address = root.findViewById(R.id.address);
        TextInputEditText occupation = root.findViewById(R.id.occupation);
        TextInputEditText monthlyIncome = root.findViewById(R.id.monthly_income);
        TextInputEditText kycProof = root.findViewById(R.id.proof_no);
        TextInputEditText referenceName = root.findViewById(R.id.reference_name);
        TextInputEditText referenceMobile = root.findViewById(R.id.reference_mobile);

        final MaterialButton create = root.findViewById(R.id.createBorrower);

        if (firstName.getText() != null &&
                fatherName.getText() != null &&
                eMail.getText() != null &&
                mobileNumber.getText() != null &&
                address.getText() != null &&
                occupation.getText() != null &&
                monthlyIncome.getText() != null &&
                kycProof.getText() != null &&
                referenceName.getText() != null &&
                referenceMobile.getText() != null) {

            create.setOnClickListener(v -> {
                BorrowerResponse.Borrower borrower = new BorrowerResponse.Borrower();
                borrower.setFirstName(firstName.getText().toString());
                borrower.setFatherName(fatherName.getText().toString());
                borrower.setEmail(eMail.getText().toString());
                borrower.setMobile(mobileNumber.getText().toString());
                borrower.setAddress(address.getText().toString());
                borrower.setOccupation(occupation.getText().toString());
                borrower.setMonthlyIncome(monthlyIncome.getText().toString());
                borrower.setProof(kycProof.getText().toString());
                borrower.setReferenceName(referenceName.getText().toString());
                borrower.setReferencMobile(referenceMobile.getText().toString());
                HttpClient.createBorrower(borrower).enqueue(new Callback<BorrowerResponse.Borrower>() {
                    @Override
                    public void onResponse(Call<BorrowerResponse.Borrower> call, Response<BorrowerResponse.Borrower> response) {
                        System.out.println("response.body() " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BorrowerResponse.Borrower> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
                System.out.println("Borrower Model : " + borrower.toString());
            });
        }
        return root;
    }
}