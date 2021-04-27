package com.example.sample.ui.loan;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.model.CollectionsResponse;
import com.example.sample.model.LoanResponse;


import java.util.ArrayList;
import java.util.Locale;

public class LoanListFragment extends Fragment implements LifecycleOwner {

    private ListViewModel listViewModel;
    private com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private SwipeRefreshLayout refresh;
    Observer<ArrayList<LoanResponse.Loan>> userListUpdateObserver = new Observer<ArrayList<LoanResponse.Loan>>() {
        @Override
        public void onChanged(ArrayList<LoanResponse.Loan> userArrayList) {
            recyclerViewAdapter = new com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter(getActivity(), userArrayList);
            refresh.setRefreshing(false);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(verticalLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        activity = ((MainActivity) getActivity());
        View root = inflater.inflate(R.layout.fragment_loan_list, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        activity.setLoanModel(listViewModel);
        listViewModel.getStringLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        activity.getLoanList();
        refresh = root.findViewById(R.id.swipe_refresh);
        refresh.setOnRefreshListener(() -> activity.getCollection());

        return root;
    }

    @Override
    public void onDetach() {
        if (activity != null) activity.setLoanModel(null);
        super.onDetach();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        if (activity !=null) activity.setCollectionModel(listViewModel);
//        super.onAttach(context);
//    }

    private static class RecyclerListAdapter extends RecyclerView.Adapter<com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter.ListViewHolder> {

        final Activity context;
        final ArrayList<LoanResponse.Loan> userArrayList;

        public RecyclerListAdapter(Activity context, ArrayList<LoanResponse.Loan> userArrayList) {
            this.userArrayList = userArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter.ListViewHolder(
                    LayoutInflater.from(
                            parent.getContext()
                    ).inflate(
                            R.layout.list_item_collection,
                            parent,
                            false
                    )
            );
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.sample.ui.loan.LoanListFragment.RecyclerListAdapter.ListViewHolder holder, int position) {
            LoanResponse.Loan listValue = userArrayList.get(position);
            System.out.println("listValue " + listValue.getFirstname());
            holder.userName.setText(listValue.getLoancode());
            holder.circleName.setText(String.valueOf(listValue.getFirstname().charAt(0)).toUpperCase());
          //  holder.pendingAmount.setText(String.format(Locale.getDefault(), "Amount - %s", listValue.getAmount()));
        }

        @Override
        public int getItemCount() {
            if (userArrayList == null)
                return 0;
            return userArrayList.size();
        }

        public static class ListViewHolder extends RecyclerView.ViewHolder {

            private final TextView userName, circleName, pendingAmount;
            private final CardView itemCard;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.userName);
                circleName = itemView.findViewById(R.id.circleName);
                pendingAmount = itemView.findViewById(R.id.userMobile);
                itemCard = itemView.findViewById(R.id.itemCard);
            }
        }
    }
}