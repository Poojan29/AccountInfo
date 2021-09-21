package com.example.angelbank.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.angelbank.datamodel.BankTable;

import java.util.List;

@Dao
public interface BankDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCustomerDetails(BankTable bankTable);

    @Delete
    void deleteCustomer(BankTable bankTable);

    @Query("SELECT * FROM AngelBank")
    LiveData<List<BankTable>> getAllCustomer();

    @Query("SELECT * FROM AngelBank WHERE customer_account = :accountNumber")
    BankTable getCustomerByAccount(String accountNumber);

}
