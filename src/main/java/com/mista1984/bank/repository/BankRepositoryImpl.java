package com.mista1984.bank.repository;

import com.mista1984.bank.app.App;
import com.mista1984.bank.db.DataBase;
import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.User;
import com.mista1984.bank.exeption.NotUniqueIdException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BankRepositoryImpl implements BankRepository{
    private static final Logger logger = LogManager.getLogger(BankRepositoryImpl.class);
    @Override
    public boolean saveBank(Bank bank) throws Exception {
        try (DataBase dataBase = DataBase.getInstance()) {
            dataBase.executeSaveOperation(bank);
            return true;
        } catch (NotUniqueIdException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Bank> getBank(int id) throws Exception {
        try (DataBase dataBase = DataBase.getInstance()) {
            return dataBase.executeGetOperation(id);
        }
    }

    @Override
    public List<Bank> getBanks() throws Exception {
        try (DataBase dataBase = DataBase.getInstance()) {
            return dataBase.executeGetAllOperation();
        }
    }

    @Override
    public void deleteBank(int id) throws Exception {
        try (DataBase dataBase = DataBase.getInstance()) {
            dataBase.executeDeleteOperation(id);
        } catch (NotUniqueIdException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> userList(int id) throws Exception {
        try (DataBase dataBase = DataBase.getInstance()) {
            return dataBase.getUsersOfBank(id);
        }
    }



}
