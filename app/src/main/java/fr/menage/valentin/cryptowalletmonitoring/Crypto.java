package fr.menage.valentin.cryptowalletmonitoring;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Crypto {
    private String name;
    private double quantity;
    private double boughtPrice;
    private String boughtDate;
    private int id;

    public Crypto(int id, String name,double boughtPrice, String boughtDate,double quantity){
        this.id=id;
        this.name=name;
        this.boughtPrice=boughtPrice;
        this.boughtDate=boughtDate;
        this.quantity=quantity;
    }

    public Crypto(int id, String name,double boughtPrice, Date boughtDate,double quantity){
        this.id=id;
        this.name=name;
        this.boughtPrice=boughtPrice;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(boughtDate);
        this.boughtDate=date;
        this.quantity=quantity;
    }

    public Crypto( String name,double boughtPrice, String boughtDate,double quantity){
        this.name=name;
        this.boughtPrice=boughtPrice;
        this.boughtDate=boughtDate;
        this.quantity=quantity;
    }

    public Crypto( String name,double boughtPrice, Date boughtDate,double quantity){
        this.name=name;
        this.boughtPrice=boughtPrice;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(boughtDate);
        this.boughtDate=date;
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

    public double getBoughtPrice() {
        return boughtPrice;
    }

    public String getName() {
        return name;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(boughtDate);
        this.boughtDate = date;
    }

    public void setBoughtPrice(double boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }
}
