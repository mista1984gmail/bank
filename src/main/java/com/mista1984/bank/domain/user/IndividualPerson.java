package com.mista1984.bank.domain.user;

import java.io.Serializable;
import java.util.List;

public class IndividualPerson extends User implements Serializable {

    public IndividualPerson(String idUser, String nameUser, List<BankAccount> bankAccountList) {
        super(idUser, nameUser, bankAccountList);
    }

    @Override
    public String toString() {
        return "IndividualPerson{" +
                "idUser='" + idUser + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", bankAccountList=" + bankAccountList +
                '}'+'\n';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
