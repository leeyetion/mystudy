package com.crc.demo.model;

/**
 * Created by crcement on 17/2/17.
 */

public class Person {
    private String name;
    private String phone;
    private String adress;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }
    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public Person(){
        super();
    }

    public Person(String name, boolean isCheck) {
        super();
        this.name = name;
        this.isCheck = isCheck;
    }

    public Person(String name, String phone,String adress){
        this.name=name;
        this.adress=adress;
        this.phone=phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}
