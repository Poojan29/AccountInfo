package com.example.angelbank.fragment;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelbank.R;
import com.example.angelbank.database.BankDatabase;
import com.example.angelbank.database.dao.BankDao;
import com.example.angelbank.datamodel.BankTable;
import com.example.angelbank.util.Constants;
import com.example.angelbank.viewmodel.BankViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.concurrent.Executor;

public class TransactionFragment extends Fragment {

    private static final int REQUEST_CODE = 101;
    private TextInputEditText accountNumber, amount;
    TextInputLayout accountLayout;
    MaterialButton validityButton, transferButton, check_my_balance;
    private LinearLayout customerDetails;
    private TextView customerName, customerAccountNumber, customerBankName, errorText;
    private BankViewModel bankViewModel;
    private NavController navController;
    private BankTable bankTable;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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

        String from_account_number = getArguments() != null ? getArguments().getString(Constants.FROM_ACCOUNT_NUMBER) : null;

        navController = Navigation.findNavController(container);

        accountNumber = view.findViewById(R.id.account_number);
        amount = view.findViewById(R.id.money);
        accountLayout = view.findViewById(R.id.account_layout);
        validityButton = view.findViewById(R.id.validity_button);
        transferButton = view.findViewById(R.id.transferButton);
        check_my_balance = view.findViewById(R.id.check_my_balance);
        customerDetails = view.findViewById(R.id.account_details);
        customerName = view.findViewById(R.id.name_of_customer);
        customerAccountNumber = view.findViewById(R.id.account_number_of_customer);
        customerBankName = view.findViewById(R.id.bank_name_of_customer);
        errorText = view.findViewById(R.id.account_not_found);

        customerDetails.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);

        executor = ContextCompat.getMainExecutor(getContext());

        biometricPrompt = new BiometricPrompt((FragmentActivity) requireContext(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String toAccountNumber = bankTable.getAccount();
                String transferred_money = amount.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FROM_ACCOUNT_NUMBER, from_account_number);
                bundle.putString(Constants.TO_ACCOUNT_NUMBER, toAccountNumber);
                bundle.putString(Constants.MONEY, transferred_money);

                navController.navigate(R.id.action_transactionFragment_to_statusFragment, bundle);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();


        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        check_my_balance.setOnClickListener(v -> {
            bankTable = bankViewModel.getCustomerByAccount(from_account_number);
            if (bankTable != null){
                String my_balance = bankTable.getMoney();
                check_my_balance.setText(my_balance);
                check_my_balance.setEnabled(false);
            } else {
                Toast.makeText(getContext(), "Ty again...", Toast.LENGTH_SHORT).show();
            }
        });

        validityButton.setOnClickListener(v -> {
            if (Objects.requireNonNull(accountNumber.getText()).toString().trim().isEmpty() || Objects.requireNonNull(amount.getText()).toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter valid details.", Toast.LENGTH_SHORT).show();
            } else if (from_account_number.equals(accountNumber.getText().toString().trim())) {
                Toast.makeText(getContext(), "You don't sent money in your account.", Toast.LENGTH_SHORT).show();
            } else if (accountNumber.getText().toString().trim().length() != 8) {
                Toast.makeText(getContext(), "Please enter valid account number", Toast.LENGTH_SHORT).show();
            } else {

                bankTable = bankViewModel.getCustomerByAccount(accountNumber.getText().toString().trim());
                Log.d("Bank", String.valueOf(bankTable));

                if (bankTable != null) {
                    validityButton.setEnabled(false);
                    customerDetails.setVisibility(View.VISIBLE);
                    errorText.setVisibility(View.GONE);
                    customerName.setText(bankTable.getName());
                    customerAccountNumber.setText(bankTable.getAccount());
                    customerBankName.setText(bankTable.getBank_name());

                    Log.d("From", from_account_number);
                    long senderTotalBalance = bankViewModel.getSenderTotalBalance(from_account_number);
                    Log.d("Sender Total", String.valueOf(senderTotalBalance));
                    long transferredMoney = Long.parseLong(amount.getText().toString());

                    bankViewModel.updateReceiverCustomerMoney(transferredMoney, accountNumber.getText().toString().trim());
                    bankViewModel.updateSenderCustomerMoney(transferredMoney, from_account_number);


                    Log.d("Receiver Money", bankTable.getMoney());
                } else {
                    validityButton.setEnabled(true);
                    customerDetails.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });

        transferButton.setOnClickListener(v -> {
            if (Objects.requireNonNull(accountNumber.getText()).toString().trim().isEmpty() || Objects.requireNonNull(amount.getText()).toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter valid details.", Toast.LENGTH_SHORT).show();
            } else if (accountNumber.getText().toString().trim().length() != 8) {
                Toast.makeText(getContext(), "Please enter valid account number", Toast.LENGTH_SHORT).show();
            } else {
                BiometricManager biometricManager = BiometricManager.from(getContext());
                switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Log.d("MY_APP_TAG", "Biometric features are currently unavailable.");
                        Toast.makeText(getContext(), "Biometric features are currently unavailable.", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        Log.e("MY_APP_TAG", "No biometric features available on this device.");
                        Toast.makeText(getContext(), "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(getContext(), "Hello here.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        startActivity(intent);
                        break;
                    case BiometricManager.BIOMETRIC_SUCCESS:
                        Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                        biometricPrompt.authenticate(promptInfo);
                        break;
                }
            }
        });

        return view;
    }
}
