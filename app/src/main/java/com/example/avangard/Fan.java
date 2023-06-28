package com.example.avangard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fan implements Serializable {
    private String name;
    private String surname;

    private String hexPassword;
    private String phone;
    private String email;
    //private List<Ticket> tickets;

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

    public String getHexPassword() {
        return hexPassword;
    }

    public void setHexPassword(String hexPassword) {
        this.hexPassword = hexPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }*/

    public Fan(String name, String surname, String hexPassword, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.hexPassword = hexPassword;
        this.phone = phone;
        this.email = email;
        //tickets = new ArrayList<>();
    }
}
