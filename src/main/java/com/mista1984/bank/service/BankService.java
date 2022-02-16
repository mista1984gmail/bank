package com.mista1984.bank.service;

import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.BankAccount;
import com.mista1984.bank.domain.user.User;

public interface BankService {
    void addBank()throws Exception;
    void saveBank(Bank bank) throws Exception;
    Bank getBank(int id) throws Exception;
    void showInfo() throws Exception;
    void deleteBank () throws Exception;
    void deleteBank (int id) throws Exception;
    void showUsersInBank() throws Exception;
    void showUserInfo(int id) throws Exception;
    void addUserInBank() throws Exception;
    User addUser() throws Exception;
    User findUser (int idBank, String idUser) throws Exception;
    BankAccount findBankAccount(User user, String numberOfAccount) throws Exception;
    void topUpAccount()throws Exception;
    void transferMoney()throws Exception;
    void addUserAccount() throws Exception;
}
