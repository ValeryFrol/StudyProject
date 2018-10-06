package data;

import java.util.Random;

public class Account {
    private String bankAccount;
    private String currency; //take currency from Money object
    private String controlNumber;
    private static final String codeBranch = "1954";
    private String userId;
    private double amount; //take currency from Money object

    /*public Account(String currency, String bankAccount, String userId) {
        setCurrency(currency);
        setBankAccount(bankAccount);
        Random rand = new Random();
        this.controlNumber = Integer.toString(rand.nextInt(10));
        setUserId(userId);
    }*/

    public Account(int currency, int bankAccount, int userId, Money money, int indicator) {
        setCurrency(Integer.toString(currency));
        setBankAccount(Integer.toString(bankAccount));
        Random rand = new Random();
        this.controlNumber = Integer.toString(rand.nextInt(10));
        setUserId(Integer.toString(userId));
        setAmount(money,indicator);
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrentAccount() {
        return bankAccount;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Money money, int indicator) {
        this.amount = amount; //create Money class and call a method which returns an amount of money that we want to pay in
        //check whether a currency of these money corresponds to the currency of given account
        //take into account that there already could be some money on the account
        //check amount of money on the account
    }

    public void setCurrency(String currency) {
        if (currency == null) {
            throw new IllegalArgumentException("currency can't be null");
        }
        this.currency = currency; //validation of a number of a currency, do we have to create a collection with all currency codes and then check the incoming currency?
    }

    public void setBankAccount(String bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("bank account can't be null");
        }
        this.bankAccount = bankAccount;
    }

    public void setUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("user ID can't be null");
        }
        this.userId = userId;
    }

    public String createAccountNumber() {
        if (this.userId.length() < 7) {
            StringBuffer sb = new StringBuffer(7);
            for (int i = 0; i < (7 - this.userId.length()); i++) {
                sb.append("0");
            }
            return this.bankAccount + this.currency + this.controlNumber + codeBranch + sb.append(this.userId);
        }
        return this.bankAccount + this.currency + this.controlNumber + codeBranch + userId.substring(0, 7);
    }

    /*public static void main(String[] args) {
        Account ac = new Account(978, 40817, 558924789,0,0);
        System.out.println(ac.createAccountNumber());
    }*/
}
