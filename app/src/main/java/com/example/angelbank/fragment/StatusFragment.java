package com.example.angelbank.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.angelbank.R;
import com.example.angelbank.util.Constants;
import com.google.android.material.button.MaterialButton;

public class StatusFragment extends Fragment {

    TextView transaction_description;
    MaterialButton goToHomeButton;
    NavController navController;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String money = getArguments() != null ? getArguments().getString(Constants.MONEY) : "0";
        String fromAccountNumber = getArguments() != null ? getArguments().getString(Constants.FROM_ACCOUNT_NUMBER) : "fromAccountNumber";
        String toAccountNumber = getArguments() != null ? getArguments().getString(Constants.TO_ACCOUNT_NUMBER) : "toAccountNumber";

        String TRANSACTION_DESCRIPTION = "You have Successfully transferred \n" + money + " \n from " + fromAccountNumber + " to " + toAccountNumber;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        navController = Navigation.findNavController(container);

        transaction_description = view.findViewById(R.id.transaction_description);
        goToHomeButton = view.findViewById(R.id.status_go_to_home);

        transaction_description.setText(TRANSACTION_DESCRIPTION);

        goToHomeButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_statusFragment_to_homeFragment);
        });

        return view;
    }
}