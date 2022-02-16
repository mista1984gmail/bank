package com.mista1984.bank.exeption;

public class IdIsNotAllowedOnDbException  extends RuntimeException{
    public IdIsNotAllowedOnDbException(int id) {
        super("The id = '" + id + "' is not allowed on DB");}
}
