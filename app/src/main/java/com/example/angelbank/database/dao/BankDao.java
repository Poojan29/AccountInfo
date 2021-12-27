package com.example.angelbank.database.dao;

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

    @Query("DELETE FROM AngelBank")
    void deleteAllData();

    @Query("SELECT * FROM AngelBank")
    LiveData<List<BankTable>> getAllCustomer();

    @Query("SELECT * FROM AngelBank WHERE customer_account = :accountNumber")
    BankTable getCustomerByAccount(String accountNumber);

    @Query("SELECT * FROM AngelBank WHERE customer_account = :accountNumber")
    boolean isCustomerAvailable(String accountNumber);

    @Query("UPDATE AngelBank SET customer_money = customer_money+:receivedMoney WHERE customer_account = :receiverAccountNumber")
    void receiverAccount(long receivedMoney, String receiverAccountNumber);

    @Query("UPDATE AngelBank SET customer_money = customer_money-:sentMoney WHERE customer_account = :senderAccountNumber")
    void senderAccount(long sentMoney, String senderAccountNumber);

    @Query("SELECT customer_money FROM AngelBank WHERE customer_account = :senderAccountNumber")
    long getSenderTotalBalance(String senderAccountNumber);
}
