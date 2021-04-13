package com.example.sample.ui.borrower;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.model.Borrower;
import com.example.sample.model.BorrowerResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements LifecycleOwner {

    private ListViewModel listViewModel;
    private RecyclerListAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_borrowers, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        listViewModel.getStringLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);

        HttpClient.getBorrowers().enqueue(new Callback<BorrowerResponse>() {
            @Override
            public void onResponse(Call<BorrowerResponse> call, Response<BorrowerResponse> response) {
                System.out.println("Responce :  "+response.code());
                if (response.body() != null) {
                    listViewModel.updateData(response.body().getBorrowersList());
                }
            }

            @Override
            public void onFailure(Call<BorrowerResponse> call, Throwable t) {
                System.out.println("Responce :  ");
                t.printStackTrace();
            }
        });

        return root;
    }

    Observer<ArrayList<Borrower>> userListUpdateObserver = new Observer<ArrayList<Borrower>>() {
        @Override
        public void onChanged(ArrayList<Borrower> userArrayList) {
            recyclerViewAdapter = new RecyclerListAdapter(getActivity(), userArrayList);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(verticalLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    private static class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ListViewHolder>{

        final Activity context;
        final ArrayList<Borrower> borrowerArrayList;
        public RecyclerListAdapter(Activity context, ArrayList<Borrower> borrowerArrayList) {
            this.borrowerArrayList = borrowerArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListViewHolder(
                    LayoutInflater.from(
                            parent.getContext()
                    ).inflate(
                            R.layout.list_item_borrower,
                            parent,
                            false
                    )
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            Borrower listValue = borrowerArrayList.get(position);
            System.out.println("listValue "+listValue.getFirstName());
            holder.userName.setText(listValue.getFirstName());
            holder.circleName.setText(String.valueOf(listValue.getFirstName().toUpperCase().charAt(0)));
            holder.pendingAmount.setText(listValue.getMobile());
            holder.delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setMessage(String.format("Are you sure to delete %s",listValue.getFirstName()));
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deleteBorrower(position);
                    // Delete Call
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.show();
            });

            holder.itemCard.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("borrower", new Gson().toJson(listValue,Borrower.class));

                intent.putExtra("userName", holder.userName.getText().toString());
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, holder.userName, "userName");
                context.startActivity(intent/*,options.toBundle()*/);
            });
        }

        @Override
        public int getItemCount() {
            return borrowerArrayList.size();
        }

        public void deleteBorrower(int position) {
            borrowerArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, borrowerArrayList.size());
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {

            private final TextView userName, circleName, pendingAmount;
            private final CardView itemCard;
            private final ImageButton delete;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.userName);
                circleName = itemView.findViewById(R.id.circleName);
                pendingAmount = itemView.findViewById(R.id.userMobile);
                delete = itemView.findViewById(R.id.delete);
                itemCard = itemView.findViewById(R.id.itemCard);
            }
        }
    }
}