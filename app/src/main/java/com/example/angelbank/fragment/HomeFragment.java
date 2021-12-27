package com.example.angelbank.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.angelbank.R;
import com.example.angelbank.adapter.CustomerAdapter;
import com.example.angelbank.datamodel.BankTable;
import com.example.angelbank.util.Constants;
import com.example.angelbank.viewmodel.BankViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    BankViewModel bankViewModel;
    CustomerAdapter customerAdapter;
    LottieAnimationView lottieAnimationView;
    NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        navController = Navigation.findNavController(container);
        recyclerView = view.findViewById(R.id.home_recycler_view);
        lottieAnimationView = view.findViewById(R.id.animationView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        customerAdapter = new CustomerAdapter();
        recyclerView.setAdapter(customerAdapter);

        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        recyclerView.post(()->bankViewModel.getAllCustomer().observe(getViewLifecycleOwner(), customer -> {
            if (customer.size() > 0){
                customerAdapter.submitCustomer(customer);
                lottieAnimationView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }else{
                lottieAnimationView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }));

        customerAdapter.setOnItemClickListener(new CustomerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(BankTable bankTable) {

                String fromAccountNumber = bankTable.getAccount();

                Bundle bundle = new Bundle();
                bundle.putString(Constants.FROM_ACCOUNT_NUMBER, fromAccountNumber);

//                Toast.makeText(getContext(), bankTable.getName(), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_homeFragment_to_transactionFragment, bundle);
            }
        });

        return view;
    }

}