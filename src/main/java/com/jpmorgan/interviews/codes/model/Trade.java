package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.api.model.Entity;
import java.util.Date;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class Trade implements Entity {

    private Stock stock = null;
    private TradeType tt = TradeType.BUY;
    private int qty = 0;
    private double price = 0.0;
    private String entityType = "trade";
    private String name = "trade";
    private long timeStamp;

    public Trade(Stock stock, int qty, TradeType tt, double price){
        this.qty = qty;
        this.stock = stock;
        this.tt = tt;
        this.price = price;
        //timeStamp = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public double getValue() {
        return this.getQuantity() * this.getPrice();
    }

    public int getQuantity(){
        return this.qty;
    }

    public void setQuantity(int qty){
        this.qty = qty;
    }

    public TradeType getTradeType(){
        return this.tt;
    }

    public void setTradeType(TradeType tt){
        this.tt = tt;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Stock getStock(){
        return this.stock;
    }

    public void setStock(Stock stock){
        this.stock = stock;
    }

}
