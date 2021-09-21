package com.example.angelbank.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelbank.R;
import com.example.angelbank.datamodel.BankTable;
import com.example.angelbank.viewmodel.BankViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class TransactionFragment extends Fragment {

    TextInputEditText accountNumber, amount;
    TextInputLayout accountLayout;
    MaterialButton validityButton, transferButton;
    LinearLayout customerDetails;
    TextView customerName, customerAccountNumber, customerBankName, errorText;
    BankViewModel bankViewModel;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        accountNumber = view.findViewById(R.id.account_number);
        amount = view.findViewById(R.id.money);
        accountLayout = view.findViewById(R.id.account_layout);
        validityButton = view.findViewById(R.id.validity_button);
        transferButton = view.findViewById(R.id.transferButton);
        customerDetails = view.findViewById(R.id.account_details);
        customerName = view.findViewById(R.id.name_of_customer);
        customerAccountNumber = view.findViewById(R.id.account_number_of_customer);
        customerBankName = view.findViewById(R.id.bank_name_of_customer);
        errorText = view.findViewById(R.id.account_not_found);

        customerDetails.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);

        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        transferButton.setOnClickListener(v -> {
            if (Objects.requireNonNull(accountNumber.getText()).toString().trim().isEmpty() || Objects.requireNonNull(amount.getText()).toString().trim().isEmpty()){
                Toast.makeText(getContext(), "Please enter valid details.", Toast.LENGTH_SHORT).show();
            }else if(accountNumber.getText().toString().trim().length() != 8 ){
                Toast.makeText(getContext(), "Please enter valid account number", Toast.LENGTH_SHORT).show();
            }else{
                String account = accountNumber.getText().toString().trim();
                Toast.makeText(getContext(), account, Toast.LENGTH_SHORT).show();
                BankTable bankTable = bankViewModel.getCustomerByAccount(account);

                Log.d("AA", String.valueOf(bankTable));
            }
        });

        return view;
    }
}