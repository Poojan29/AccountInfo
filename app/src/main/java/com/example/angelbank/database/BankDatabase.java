package com.example.angelbank.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.angelbank.database.dao.BankDao;
import com.example.angelbank.datamodel.BankTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BankTable.class}, version = 1)
public abstract class BankDatabase extends RoomDatabase {

    public abstract BankDao bankDao();

    private static volatile BankDatabase INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BankDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (BankDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,
                            BankDatabase.class, "BankDatabase").build();
                }
            }
        }
        return INSTANCE;
    }

}
