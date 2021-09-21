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

}
