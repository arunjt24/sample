package com.example.sample.ui.collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListFragment extends Fragment implements LifecycleOwner {

    private ListViewModel listViewModel;
    private RecyclerListAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_collections, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        listViewModel.getStringLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        return root;
    }

    Observer<ArrayList<ListViewModel.User>> userListUpdateObserver = new Observer<ArrayList<ListViewModel.User>>() {
        @Override
        public void onChanged(ArrayList<ListViewModel.User> userArrayList) {
            recyclerViewAdapter = new RecyclerListAdapter(getActivity(), userArrayList);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(verticalLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    private static class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ListViewHolder>{

        final Activity context;
        final ArrayList<ListViewModel.User> userArrayList;
        public RecyclerListAdapter(Activity context, ArrayList<ListViewModel.User> userArrayList) {
            this.userArrayList = userArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListViewHolder(
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
            ListViewModel.User listValue = userArrayList.get(position);
            System.out.println("listValue "+listValue.name);
            holder.userName.setText(listValue.name);
            holder.circleName.setText(String.valueOf(listValue.name.charAt(0)));
            holder.pendingAmount.setText(String.format(Locale.getDefault(),"PENDING - %d",listValue.pendingAmount));
            holder.itemCard.setOnClickListener(v -> {
                /*Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("userName", holder.userName.getText().toString());

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, holder.userName, "userName");

                context.startActivity(intent, options.toBundle());*/
            });
        }

        @Override
        public int getItemCount() {
            return userArrayList.size();
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {

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