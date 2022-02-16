package com.mista1984.bank.domain.user;

import java.io.Serializable;

public enum Currency implements Serializable {
    BYN(1,"BYN"),
    RUB(1,"RUB"),
    USD(1,"USD");

    private int id;
    private String name;

    Currency(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
