package com.example.angelbank.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = BankTable.BANK_TABLE)
public class BankTable {

    public static final String BANK_TABLE = "AngelBank";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "customer_name";
    public static final String ACCOUNT_COLUMN = "customer_account";
    public static final String BANK_COLUMN = "customer_bank";
    public static final String MONEY_COLUMN = "customer_money";

    @ColumnInfo(name = BankTable.ID_COLUMN)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = NAME_COLUMN)
    private String name;

    @ColumnInfo(name = ACCOUNT_COLUMN)
    private String account;

    @ColumnInfo(name = BANK_COLUMN)
    private String bank_name;

    @ColumnInfo(name = MONEY_COLUMN)
    private String money;

    public BankTable(String name, String account, String bank_name, String money) {
        this.name = name;
        this.account = account;
        this.bank_name = bank_name;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
