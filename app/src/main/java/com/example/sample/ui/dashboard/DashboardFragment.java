package com.example.sample.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.model.CollectionsResponse;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private RecyclerListAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private SwipeRefreshLayout refresh;
    Observer<ArrayList<CollectionsResponse.Collection>> userListUpdateObserver = new Observer<ArrayList<CollectionsResponse.Collection>>() {
        @Override
        public void onChanged(ArrayList<CollectionsResponse.Collection> userArrayList) {
            recyclerViewAdapter = new RecyclerListAdapter(getActivity(), userArrayList);
            refresh.setRefreshing(false);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(verticalLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        activity = ((MainActivity) getActivity());
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        activity.setDashboardModel(dashboardViewModel);
        dashboardViewModel.getStringLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        activity.getCollection();
        refresh = root.findViewById(R.id.swipe_refresh);
        refresh.setOnRefreshListener(() -> activity.getTodayCollection());

        return root;
    }

    @Override
    public void onDetach() {
        if (activity != null) activity.setCollectionModel(null);
        super.onDetach();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        if (activity !=null) activity.setCollectionModel(dashboardViewModel);
//        super.onAttach(context);
//    }

    private static class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ListViewHolder> {

        final Activity context;
        final ArrayList<CollectionsResponse.Collection> userArrayList;

        public RecyclerListAdapter(Activity context, ArrayList<CollectionsResponse.Collection> userArrayList) {
            this.userArrayList = userArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerListAdapter.ListViewHolder(
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
        public void onBindViewHolder(@NonNull RecyclerListAdapter.ListViewHolder holder, int position) {
            CollectionsResponse.Collection listValue = userArrayList.get(position);
            System.out.println("listValue " + listValue.getFirstname());
            holder.userName.setText(listValue.getFirstname());
            holder.circleName.setText(String.valueOf(listValue.getFirstname().charAt(0)).toUpperCase());
            holder.pendingAmount.setText(String.format(Locale.getDefault(), "Amount - %s", listValue.getAmount()));
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