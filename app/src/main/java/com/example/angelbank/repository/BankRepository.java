package com.example.angelbank.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.angelbank.database.BankDatabase;
import com.example.angelbank.database.dao.BankDao;
import com.example.angelbank.datamodel.BankTable;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BankRepository {

    private final BankDao bankDao;
    private final LiveData<List<BankTable>> allCustomer;
    private BankTable bankTable;
    private boolean isCustomerAvailable;
    private long senderTotalBalance;

    public BankRepository(Application application) {
        BankDatabase bankDatabase = BankDatabase.getInstance(application);
        bankDao = bankDatabase.bankDao();
        allCustomer = bankDao.getAllCustomer();
    }

    public LiveData<List<BankTable>> getAllCustomer() {
        return allCustomer;
    }

    public void insertCustomer(BankTable bankTable) {
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.addCustomerDetails(bankTable);
        });
    }

    public BankTable getCustomerByAccount(String accountNumber) {
        // Similar like AsyncTask
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankTable = bankDao.getCustomerByAccount(accountNumber);
        });
        return bankTable;
    }

    public void deleteCustomer(BankTable bankTable) {
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.deleteCustomer(bankTable);
        });
    }

    public void deleteAllData(){
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.deleteAllData();
        });
    }

    public boolean isCustomerAvailable(String accountNumber){
        BankDatabase.databaseWriteExecutor.execute(() -> {
            isCustomerAvailable = bankDao.isCustomerAvailable(accountNumber);
        });
        return isCustomerAvailable;
    }

    public void updateReceiverCustomerMoney(long receiverMoney, String receiverAccountNumber){
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.receiverAccount(receiverMoney, receiverAccountNumber);
        });
    }

    public void updateSenderCustomerMoney(long sentMoney, String senderAccountNumber){
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.senderAccount(sentMoney, senderAccountNumber);
        });
    }

    public long getSenderTotalBalance(String senderAccountNumber){
        BankDatabase.databaseWriteExecutor.execute(() -> {
            bankDao.getSenderTotalBalance(senderAccountNumber);
        });
        return senderTotalBalance;
    }

}
