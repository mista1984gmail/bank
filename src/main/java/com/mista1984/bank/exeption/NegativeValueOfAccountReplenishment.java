package com.mista1984.bank.exeption;

public class NegativeValueOfAccountReplenishment extends Exception{
    public NegativeValueOfAccountReplenishment(String message) {
        super("Negative value of account replenishment");
    }
}
