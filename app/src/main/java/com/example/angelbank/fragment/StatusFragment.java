package com.example.angelbank.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.angelbank.R;

public class StatusFragment extends Fragment {

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

        long fromAccountNumber = 0, toAccountNumber = 0, money = 0;
        String TRANSACTION_DESCRIPTION = "You have Successfully transferred\n" + money + "\nfrom " + fromAccountNumber + " to " + toAccountNumber;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        return view;
    }
}