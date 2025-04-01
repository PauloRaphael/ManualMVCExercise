package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Account {
    private final Person owner;
    private double balance;
    private final int accountNumber;
    private String accountPassword;

    private Account(Person owner, double balance, int accountNumber, String accountPassword) {
        this.owner = owner;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
    }

    public static Account getAccount(Person owner, double balance, String accountPassword) {
        if (owner == null) {
            throw new NullPointerException("owner is null");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("balance is negative");
        }
        if (!isAdult(owner)) {
            throw new IllegalArgumentException("owner is below 18");
        }
        if(accountPassword.length() < 8) {
            throw new IllegalArgumentException("Account Password is too short");
        }
        return new Account(owner, balance, ThreadLocalRandom.current().nextInt(10000), accountPassword);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        if (accountPassword.length() < 8) {
            throw new IllegalArgumentException("Account Password is too short");
        }
        this.accountPassword = accountPassword;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public Person getOwner() {
        return owner;
    }

    public void withdrawal(double amount) {
        if(amount > balance) {
            throw new IllegalArgumentException("Trying to withdraw more than balance");
        }
        balance -= amount;
    }

    public void deposit(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Trying to deposit negative amount");
        }
        balance += amount;
    }

    public void transfer(double amount, Account account) {
        if(amount > balance) {
            throw new IllegalArgumentException("Trying to transfer more than balance");
        }
        if(account == null) {
            throw new NullPointerException("account is null");
        }
        if(amount < 0) {
            throw new IllegalArgumentException("Trying to transfer negative amount");
        }
        if(account.accountNumber == this.accountNumber) {
            throw new IllegalArgumentException("Trying to transfer to own account");
        }
        balance -= amount;
        account.deposit(amount);
    }

    private static boolean isAdult(Person owner) {
        LocalDate adultThreshold = LocalDate.now().minusYears(18);
        return owner.getDateOfBirth().before(Date.valueOf(adultThreshold));
    }
}