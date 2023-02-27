package com.example.cornapp.data.models;

public class UserBo {
    private String name, surname, email;
    private int phone;

    public UserBo(String _name, String _surname, String _email, int _phone){
        this.name = _name;
        this.surname = _surname;
        this.email = _email;
        this.phone = _phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
