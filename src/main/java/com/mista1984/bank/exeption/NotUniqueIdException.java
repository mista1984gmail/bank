package com.mista1984.bank.exeption;

import com.mista1984.bank.domain.bank.Bank;

public class NotUniqueIdException extends Exception{
    public NotUniqueIdException(Bank bank) {
        super("Bank is not saved: " + bank);
    }
}
