package com.example.sample.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sample.R;
import com.example.sample.util.Preference;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final MaterialTextView textView = root.findViewById(R.id.text_dashboard);
        String branch = Preference.getBranchName();
        System.out.println("branch.........." + branch);
        if (branch.length() > 0)
            dashboardViewModel.setWelcomeMessage(String.format(Locale.getDefault(), "Welcome to %s branch", branch.replaceFirst(String.valueOf(branch.charAt(0)), String.valueOf(branch.charAt(0)).toUpperCase())));
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}