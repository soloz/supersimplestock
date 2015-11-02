package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.api.model.Entity;
import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;

/**
 * Created by sadebayo on 27/10/2015.
 */
public abstract class Stock implements Entity, Cloneable {
    private double ZERO = 0.0;
    private String symbol;
    private String name;
    private String type;
    private String entityType;
    private double price;
    private double dividendYield;
    private double peRatio;
    private double lastDividendValue = ZERO;
    private double parValue = ZERO;
    private double marketPrice = ZERO;
    private long timeStamp;
    private int qty;

    /**
     * Default constructor for Stock. Sets default values.
     */
    public Stock(){
        this.entityType = "Stock";
        this.type = "Stock";
        this.price = price;
        this.dividendYield = ZERO;
        this.peRatio = ZERO;
        this.symbol = "";
        this.name = "Stock";
    }

    /**
     * Constructor for Stock
     * @params double lastDividendValue: Last dividend value.
     *         double parValue: Par value for stock.
     *         Sting symbol: Stock symbol.
     */
    public Stock(double lastDividendValue, double parValue, String symbol){
        this.lastDividendValue = lastDividendValue;
        this.parValue = parValue;
        this.symbol = symbol;
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * Constructor for Stock
     * @params double lastDividendValue: Last dividend value.
     *         double parValue: Par value for stock.
     *         double price: Market price for stock
     *         Sting symbol: Stock symbol.
     */
    public Stock(double lastDividendValue, double parValue, double price, String symbol){
        this.lastDividendValue = lastDividendValue;
        this.parValue = parValue;
        this.symbol = symbol;
        this.marketPrice = price;
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * Method to set the name of entity (stock)
     * @return String entityType: Entity type.
     */
    public void setEntityType(String entityType){
        this.entityType = entityType;
    }

    /**
     * Method to set the name of entity (stock)
     * @params String name: Name of entity (stock).
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to set the type of entity
     * @return String type: Type of entity.
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Method to set the quantity of stock
     * @params int qty: Quantity of stock.
     */
    public void setQuantity(int qty){
        this.qty = qty;
    }

    /**
     * Method to get the name of entity (stock)
     * @return String name: Name of entity (stock).
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method to get the type of stock
     * @return String type: Type of stock.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Method to get the entity type (stock)
     * @return String entityType: Entity type.
     */
    public String getEntityType(){
        return this.entityType;
    }

    /**
     * Method to get the quantity of stock
     * @return int qty: Quantity of stock.
     */
    public int getQuantity(){
        return this.qty;
    }


    /**
     * Method compute the PE ratio for a stock
     * @params double price: Market price for stock
     * @return double PERatio : Stock's PE Ratio.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computePERatio() throws Exception {
        if (this.getMarketValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock maket price cannot be negative");

        if (this.computeDividendYield() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock yield in dividend cannot be negative");

        if (this.computeDividendYield() == ZERO)
            throw new SimpleStocksExceptionImpl("Divide by Zero Error: Stock yield in dividend cannot be zero");

        return this.getMarketValue() / this.computeDividendYield();

    }

    /**
     * Abstract method for computing dividend yield
     * @return double PERatio : PE Ratio value for a stock.
     */
    public abstract double computeDividendYield() throws Exception;

    /**
     * Method compute the dividend yield for a stock, given price
     * @params double price: Market price for stock
     * @return double dividendYield : Stock's dividend yield given price.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeDividendYield(double price) throws Exception{
        this.setMarketValue(price);
        return computeDividendYield();
    }

    /**
     * Method to compute the PE ratio for a stock, given price
     * @params double price: Market price for stock
     * @return double PERatio : Computed PE Ratio value for a stock.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computePERatio(double price) throws Exception{
        this.setMarketValue(price);
        return computePERatio();
    }

    /**
     * Method to get computed stock's value per share.
     * @params double marketPrice: Stock's last dividend value / price.
     */
    public double getValue() {
        return this.getMarketValue() * this.getQuantity();
    }

    /**
     * Method to set the stock's last dividend value / price.
     * @params double marketPrice: Stock's last dividend value / price.
     */
    public void setLastDividendValue(double lastDividendValue){
        this.lastDividendValue = lastDividendValue;
    }

    /**
     * Method to set the stock's par value / price.
     * @params double marketPrice: Stock's par price.
     */
    public void setParValue(double parValue){
        this.parValue = parValue;
    }

    /**
     * Method to set the stock's market value / price.
     * @params double marketPrice: Stock's market price.
     */
    public void setMarketValue(double price){
        this.marketPrice = price;
    }

    /**
     * Method to set the stock symbol.
     * @params String symbol : Stock's symbol.
     */
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    /**
     * Method to get the computed dividend yeild for stock, given price.
     * @return double dividendYield : Stock's dividend yield.
     */

    public double getDividendYield(){
        return this.dividendYield;
    }

    /**
     * Method to get the computed PE ratio for a stock
     * @return double PERatio : PE Ratio value for a stock.
     */
    public double getPERatio(){
        return  this.getDividendYield();
    }

    /**
     * Method to get the last dividend value  for stock.
     * @return double lastDividendValue : Last dividend value for stock.
     */
    public double getLastDividendValue(){
        return this.lastDividendValue;
    }

    /**
     * Method to get the par value for stock
     * @return double parValue: Par value for stock.
     */
    public double getParValue(){
        return this.parValue;
    }

    /**
     * Method to get the market price for stock
     * @return double marketValue : Stock market price.
     */
    public double getMarketValue(){
        return this.marketPrice;
    }

    /**
     * Method to get the symbol for stock
     * @return String symbol : Stock symbol.
     */
    public  String getSymbol(){
        return this.symbol;
    }

    /**
     * Method to set the timestamp for stock
     * @params long timeStamp : Timestamp for stock creation.
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Method to get the timestamp for stock creation
     * @return long timeStamp : Timestamp for stock creation.
     */
    public long getTimeStamp() {
        return this.timeStamp;
    }


    /**
     * Method to clone a common stock
     * @return Stock stock: a copy of an instance of a common stock
     */
    public Stock clone() {
        try {
            return (Stock) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
