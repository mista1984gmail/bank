package com.mista1984.bank.util;

import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.User;

import java.util.List;

public class BankFixture {
    private static final String NAME="BPS BANK";
    private static double interestRateForIndividuals = 5;
    private static double interestRateForOrganizations = 7;
    private static List<User>userList;

    public static Bank createBank(){
        Bank bank = new Bank();
        bank.setNameBank(NAME);
        bank.setInterestRateForIndividuals(interestRateForIndividuals);
        bank.setInterestRateForOrganizations(interestRateForOrganizations);
        bank.setUserList(userList);
        return bank;
    }
}
