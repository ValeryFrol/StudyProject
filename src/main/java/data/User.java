package data;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String surname;
    private String address;
    private String phone;
    private String email;
    private int id;
    private int age;
    private Map<String, Account> allUserAccounts = new HashMap<String, Account>();

    public User(String name, String surname, String address, String phone, String email, int id, int age) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.age = age;
    }

    public User(String name, String surname, String address, String phone, String email, int id, int age, Account account) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.age = age;
        this.allUserAccounts.put(account.createAccountNumber(),account);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Account> getAllUserAccounts() {
        return allUserAccounts;
    }
    //why cannot i place an annotation here? like @override

    public int hashCode(String id, String name, String email) {
        int hash = 5;
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        return hash;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.name.equals(user.name) && this.surname.equals(user.surname) && this.address.equals(user.address) && this.phone.equals(user.phone) && this.email.equals(user.email) && this.id == user.id && this.age == user.age;
    }

    public void addAccount(Account account) {
        this.allUserAccounts.put(account.createAccountNumber(),account);
    }

    public void cashFlow(Account account,Money money,int indicator) {
        this.allUserAccounts.get(account.createAccountNumber()).setAmount(money,indicator);
    }

    public void closeAccount(Account account) {
        boolean remove = this.allUserAccounts.remove(account.createAccountNumber(), account);
    }

}
