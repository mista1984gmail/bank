package com.mista1984.bank.domain.bank;

import com.mista1984.bank.domain.user.User;

import java.io.Serializable;
import java.util.List;

public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nameBank;
    private int id;
    private double interestRateForIndividuals;
    private double interestRateForOrganizations;
    private List<User>userList;

    public Bank() {
    }

    public Bank(int id) {
        this.id = id;
    }

    public Bank(String nameBank, int id, double interestRateForIndividuals, double interestRateForOrganizations, List<User> userList) {
        this.nameBank = nameBank;
        this.id = id;
        this.interestRateForIndividuals = interestRateForIndividuals;
        this.interestRateForOrganizations = interestRateForOrganizations;
        this.userList = userList;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInterestRateForIndividuals() {
        return interestRateForIndividuals;
    }

    public void setInterestRateForIndividuals(double interestRateForIndividuals) {
        this.interestRateForIndividuals = interestRateForIndividuals;
    }

    public double getInterestRateForOrganizations() {
        return interestRateForOrganizations;
    }

    public void setInterestRateForOrganizations(double interestRateForOrganizations) {
        this.interestRateForOrganizations = interestRateForOrganizations;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "nameBank='" + nameBank + '\'' +
                ", id=" + id +
                ", interestRateForIndividuals=" + interestRateForIndividuals +
                ", interestRateForOrganizations=" + interestRateForOrganizations +
                ", userList=" + userList +
                '}';
    }
}
