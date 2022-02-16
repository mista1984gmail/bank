package com.mista1984.bank.repository;

import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    boolean saveBank(Bank bank) throws Exception;
    Optional<Bank> getBank(int id) throws Exception;
    List<Bank> getBanks() throws Exception;
    void deleteBank(int id) throws Exception;
    List<User> userList (int id) throws Exception;

}
