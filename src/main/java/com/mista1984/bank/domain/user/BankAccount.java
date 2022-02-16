package com.mista1984.bank.domain.user;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {

        String numberOfBankAccount;
        double balance;
        Currency currency;

        public BankAccount(String numberOfBankAccount, double balance, Currency currency) {
                this.numberOfBankAccount = numberOfBankAccount;
                this.balance = balance;
                this.currency = currency;
        }

        public String getNumberOfBankAccount() {
                return numberOfBankAccount;
        }

        public void setNumberOfBankAccount(String numberOfBankAccount) {
                this.numberOfBankAccount = numberOfBankAccount;
        }

        public double getBalance() {
                return balance;
        }

        public void setBalance(double balance) {
                this.balance = balance;
        }

        public Currency getCurrency() {
                return currency;
        }

        public void setCurrency(Currency currency) {
                this.currency = currency;
        }

        @Override
        public String toString() {
                return "BankAccount{" +
                        "numberOfBankAccount='" + numberOfBankAccount + '\'' +
                        ", balance=" + balance +
                        ", currency=" + currency +
                        '}';
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                BankAccount that = (BankAccount) o;
                return Double.compare(that.balance, balance) == 0 && Objects.equals(numberOfBankAccount, that.numberOfBankAccount) && currency == that.currency;
        }

        @Override
        public int hashCode() {
                return Objects.hash(numberOfBankAccount, balance, currency);
        }
}
