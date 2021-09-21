package com.example.angelbank.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.angelbank.R;
import com.example.angelbank.datamodel.BankTable;
import com.example.angelbank.viewmodel.BankViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddCustomerFragment extends Fragment {

    TextInputEditText name, account_number, bank_name, money;
    TextInputLayout accountLayout;
    MaterialButton confirm_customer;
    BankViewModel bankViewModel;
    NavController navController;

    public AddCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);

        name = view.findViewById(R.id.customer_name);
        account_number = view.findViewById(R.id.customer_account_number);
        bank_name = view.findViewById(R.id.customer_bank_name);
        money = view.findViewById(R.id.customer_money);
        accountLayout = view.findViewById(R.id.customer_account_layout);
        confirm_customer = view.findViewById(R.id.confirm_customer);
        navController = Navigation.findNavController(container);

        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        confirm_customer.setOnClickListener(v -> {
            if (isDetailsCompleted()){
                if (account_number.length()<8){
                    accountLayout.setErrorEnabled(true);
                    accountLayout.setError("Please enter correct account number");
                }else {
                    bankViewModel.insertCustomer(new BankTable(Objects.requireNonNull(name.getText()).toString().trim(),
                            Objects.requireNonNull(account_number.getText()).toString().trim(),
                            Objects.requireNonNull(bank_name.getText()).toString().trim(),
                            Objects.requireNonNull(money.getText()).toString().trim()
                    ));

                    new Handler().postDelayed(() -> {
                        navController.navigateUp();
                    }, 200);
                }
            }
        });

        return view;
    }

    public boolean isDetailsCompleted(){
        if (Objects.requireNonNull(name.getText()).toString().trim().isEmpty()
                || Objects.requireNonNull(account_number.getText()).toString().trim().isEmpty()
                || Objects.requireNonNull(bank_name.getText()).toString().trim().isEmpty()
                || Objects.requireNonNull(money.getText()).toString().trim().isEmpty()
        ){
            return false;
        }
        return true;
    }
}