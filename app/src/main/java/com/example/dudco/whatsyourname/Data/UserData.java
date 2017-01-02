package com.example.dudco.whatsyourname.Data;

/**
 * Created by dudco on 2017. 1. 2..
 */

public class UserData {
    private String id, pass, name, phone, birth, belong, career, email, des, pax, name_card;

    public UserData(String id, String pass, String name, String phone, String birth, String belong, String career,
                    String email, String des, String pax, String name_card) {
        this.id = id;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.belong = belong;
        this.career = career;
        this.email = email;
        this.des = des;
        this.pax = pax;
        this.name_card = name_card;
    }

    public String getBelong() {
        return belong;
    }

    public String getCareer() {
        return career;
    }

    public String getEmail() {
        return email;
    }

    public String getDes() {
        return des;
    }

    public String getPax() {
        return pax;
    }

    public String getName_card() {
        return name_card;
    }

    public String getBirth() {
        return birth;
    }

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
