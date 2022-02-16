package com.mista1984.bank.exeption;

public class NotEnoughMoneyInTheAccount extends Exception{
    public NotEnoughMoneyInTheAccount(String message) {
        super("Not enough money in the account");
    }
}
