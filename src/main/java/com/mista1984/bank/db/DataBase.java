package com.mista1984.bank.db;

import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.User;
import com.mista1984.bank.exeption.IdIsNotAllowedOnDbException;
import com.mista1984.bank.exeption.NotUniqueIdException;
import com.mista1984.bank.io.SerializationUtils;

import java.io.Serializable;
import java.util.*;

public class DataBase implements Serializable,AutoCloseable{
    private Map<Integer, Bank> BANKS;

    private static final int MAX_ALLOWED_ID = 100;
    private static DataBase instance = new DataBase();
    private int currentId = 0;

    private DataBase() {
        BANKS = new HashMap<>();}

    public static DataBase getInstance(){
        return instance;
    }

    public void executeSaveOperation(Bank bank)
            throws NotUniqueIdException {
        bank.setId(currentId);
        if (BANKS.containsKey(bank.getId())) {
            throw new NotUniqueIdException(bank);
        }
        BANKS.put(currentId++, bank);
    }

    public Optional<Bank> executeGetOperation(int id) {
        if (id > MAX_ALLOWED_ID) {
            throw new IdIsNotAllowedOnDbException(id);
        }
        return Optional.ofNullable(BANKS.get(id));
    }

    public void save() {
        SerializationUtils.serialize(this);
    }


    public void load() {
        Object deserialized = SerializationUtils.deserialize();
        if (deserialized instanceof DataBase) {
            instance = (DataBase) deserialized;
        }
    }
    public List<Bank> executeGetAllOperation() {
        return new ArrayList<>(BANKS.values());
    }

    public void executeDeleteOperation(int id){
        BANKS.remove(id);
        DataBase.getInstance().save();
    }
    public List<User> getUsersOfBank(int id){
        return BANKS.get(id).getUserList();

    }

    public void clearDb() {
        BANKS = new HashMap<>();
        currentId = 0;
    }

    @Override
    public void close() throws Exception {

    }
}
