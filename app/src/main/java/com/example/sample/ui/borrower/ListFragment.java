package com.example.sample.ui.borrower;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sample.MainActivity;
import com.example.sample.R;
import com.example.sample.client.HttpClient;
import com.example.sample.model.BorrowerResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements LifecycleOwner, MainActivity.Listener {

    TextInputEditText firstName;
    TextInputEditText fatherName;
    TextInputEditText eMail;
    TextInputEditText mobileNumber;
    TextInputEditText address;
    TextInputEditText occupation;
    TextInputEditText monthlyIncome;
    TextInputEditText kycProof;
    TextInputEditText referenceName;
    TextInputEditText referenceMobile;
    private ListViewModel listViewModel;
    private RecyclerListAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private View borrowerCard;
    private View updateButton;
    private String borrowersId;
    private MainActivity activity;
    private SwipeRefreshLayout refresh;
    Observer<ArrayList<BorrowerResponse.Borrower>> userListUpdateObserver = new Observer<ArrayList<BorrowerResponse.Borrower>>() {
        @Override
        public void onChanged(ArrayList<BorrowerResponse.Borrower> userArrayList) {
            recyclerViewAdapter = new RecyclerListAdapter(getActivity(), userArrayList);
            refresh.setRefreshing(false);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(verticalLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };
    private ProgressBar loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_borrowers, container, false);
        activity = (MainActivity) getActivity();
        activity.setBorrowerModel(listViewModel);
        activity.addListener(this);
        recyclerView = root.findViewById(R.id.recycler_view);
        borrowerCard = root.findViewById(R.id.borrowerCard);

        firstName = root.findViewById(R.id.first_name);
        fatherName = root.findViewById(R.id.father_name);
        eMail = root.findViewById(R.id.e_mail);
        mobileNumber = root.findViewById(R.id.mobile_number);
        address = root.findViewById(R.id.address);
        occupation = root.findViewById(R.id.occupation);
        monthlyIncome = root.findViewById(R.id.monthly_income);
        kycProof = root.findViewById(R.id.proof_no);
        referenceName = root.findViewById(R.id.reference_name);
        referenceMobile = root.findViewById(R.id.reference_mobile);

        updateButton = root.findViewById(R.id.updateBorrower);
        loader = root.findViewById(R.id.progress);
        updateButton.setOnClickListener(v -> {
            updateButton.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
            BorrowerResponse.Borrower editBorrower = new BorrowerResponse.Borrower();
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
            System.out.println("Borrower Model : " + new Gson().toJson(editBorrower, BorrowerResponse.Borrower.class));
            HttpClient.editBorrower(editBorrower).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    System.out.println("response.body() " + response.code());
                    System.out.println("response.body() " + response.body().toString());
                    Toast.makeText(getActivity(), "Collection Updated!",
                            Toast.LENGTH_LONG).show();
                    borrowerCard.setVisibility(View.GONE);
                    updateButton.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    updateButton.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Promblem in Collection Update!",
                            Toast.LENGTH_LONG).show();
                }
            });
        });
        listViewModel.getStringLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
//        listViewModel.updateData(activity.getBorrowersList());
        activity.getBorrower();
        refresh = root.findViewById(R.id.swipe_refresh);
        refresh.setOnRefreshListener(() -> activity.getBorrower());

        return root;
    }

    @Override
    public void onDetach() {
        activity.setBorrowerModel(null);
        super.onDetach();
    }

    public void refreshBorrowers() {
        HttpClient.getBorrowers().enqueue(new Callback<BorrowerResponse>() {
            @Override
            public void onResponse(Call<BorrowerResponse> call, Response<BorrowerResponse> response) {
                System.out.println("Responce :  " + response.code());
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
    }

    @Override
    public boolean onBackPressed() {
        if (borrowerCard.getVisibility() == View.VISIBLE) {
            borrowerCard.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    private class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ListViewHolder> {

        final Activity context;
        final ArrayList<BorrowerResponse.Borrower> borrowerArrayList;

        public RecyclerListAdapter(Activity context, ArrayList<BorrowerResponse.Borrower> borrowerArrayList) {
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
            BorrowerResponse.Borrower listValue = borrowerArrayList.get(position);
            System.out.println("listValue " + listValue.getFirstName());
            holder.userName.setText(listValue.getFirstName());
            holder.circleName.setText(String.valueOf(listValue.getFirstName().toUpperCase().charAt(0)));
            holder.pendingAmount.setText(listValue.getMobile());
            holder.delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setMessage(String.format("Are you sure to delete %s", listValue.getFirstName()));
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deleteBorrower(position);
                    // Delete Call
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.show();
            });

            holder.itemCard.setOnClickListener(v -> {

                Animation test = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
                borrowerCard.setVisibility(View.VISIBLE);
                borrowerCard.startAnimation(test);
                borrowersId = listValue.getBorrowersId();
                firstName.setText(listValue.getFirstName());
                fatherName.setText(listValue.getFatherName());
                eMail.setText(listValue.getEmail());
                mobileNumber.setText(listValue.getMobile());
                address.setText(listValue.getAddress());
                occupation.setText(listValue.getOccupation());
                monthlyIncome.setText(listValue.getMonthlyIncome());
                kycProof.setText(listValue.getProof());
                referenceName.setText(listValue.getReferenceName());
                referenceMobile.setText(listValue.getReferencMobile());
//                Intent intent = new Intent(context, EditActivity.class);
//                intent.putExtra("borrower", new Gson().toJson(listValue,Borrower.class));

//                intent.putExtra("userName", holder.userName.getText().toString());
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, holder.userName, "userName");
//                context.startActivity(intent/*,options.toBundle()*/);
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