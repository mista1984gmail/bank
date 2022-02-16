package com.mista1984.bank.domain.user;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class User implements Comparable, Serializable {
    String idUser;
    String nameUser;
    List<BankAccount>bankAccountList;

    public User(String idUser, String nameUser, List<BankAccount> bankAccountList) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.bankAccountList = bankAccountList;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", bankAccountList=" + bankAccountList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) && Objects.equals(nameUser, user.nameUser) && Objects.equals(bankAccountList, user.bankAccountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, nameUser, bankAccountList);
    }
}
