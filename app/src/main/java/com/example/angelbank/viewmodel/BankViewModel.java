package com.example.angelbank.viewmodel;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.angelbank.database.BankDatabase;
import com.example.angelbank.datamodel.BankTable;
import com.example.angelbank.repository.BankRepository;

import java.util.List;

public class BankViewModel extends AndroidViewModel {

    private final BankRepository bankRepository;
    private final LiveData<List<BankTable>> allCustomer;

    public BankViewModel(@NonNull Application application) {
        super(application);
        bankRepository = new BankRepository(application);
        allCustomer = bankRepository.getAllCustomer();
    }

    public LiveData<List<BankTable>> getAllCustomer(){
        return allCustomer;
    }

    public void insertCustomer(BankTable bankTable){
        bankRepository.insertCustomer(bankTable);
    }

    public BankTable getCustomerByAccount(String accountNumber){
        return bankRepository.getCustomerByAccount(accountNumber);
    }

    public void deleteCustomer(BankTable bankTable){
        bankRepository.deleteCustomer(bankTable);
    }

    public void deleteAllData(){
        bankRepository.deleteAllData();
    }

    public boolean isCustomerAvailable(String accountNumber){
        return bankRepository.isCustomerAvailable(accountNumber);
    }

    public void updateReceiverCustomerMoney(long receivedMoney, String receiverAccountNumber){
        bankRepository.updateReceiverCustomerMoney(receivedMoney, receiverAccountNumber);
    }

    public void updateSenderCustomerMoney(long sentMoney, String senderAccountNumber){
        bankRepository.updateSenderCustomerMoney(sentMoney, senderAccountNumber);
    }

    public long getSenderTotalBalance(String senderAccountNumber){
        return bankRepository.getSenderTotalBalance(senderAccountNumber);
    }
}
