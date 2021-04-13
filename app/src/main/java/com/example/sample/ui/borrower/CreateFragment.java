package com.example.sample.ui.borrower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sample.R;
import com.example.sample.model.Borrower;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

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

        createViewModel.getBorrower().observe(getViewLifecycleOwner(), borrower -> {
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
        });

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

            create.setOnClickListener(v -> {
                Borrower borrower = new Borrower();
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
                System.out.println("Borrower Model : "+borrower.toString());
            });
        }
        return root;
    }
}