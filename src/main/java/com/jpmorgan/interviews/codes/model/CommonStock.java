package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;

/**
 * Stock class implementation representing common stock type
 * Created by sadebayo on 28/10/2015.
 */
public class CommonStock extends Stock{
    private final double ZERO = 0.0;

    private String symbol;
    private String type;
    private double dividendYield;
    private String name;
    private double lastDividendValue = ZERO;
    private double parValue = ZERO;
    private double marketPrice = ZERO;
    private double peRatio = ZERO;

    /**
     * Default constructor.
     * Initialises default (empty) trades.
     */
    public CommonStock(){
        super();
    }

    /**
     * Common Stock constructor to initialise stock properties.
     * @params double lastDividendValue: Last dividend value for stock.
     *         double parValue
     *         String symbol: Stock symbol
     */
    public CommonStock(double lastDividendValue, double parValue, String symbol){
        super(lastDividendValue, parValue, symbol);
    }

    /**
     * Method to set name of stock.
     * @params String name: Name of stock. Equivalent to symbol.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to get name of stock.
     * @return String name: Name of stock. Equivalent to symbol.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method to set type of entity (i.e. a stock).
     * @params String type: Type of entity. A stock.
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Method to get type of entity.
     * @return String type: Type of entity - a stock.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Method to compute dividend yield for a common stock
     * @return double dividendYield : Yield in dividend for this stock.
     *          It requires that market price is set
     * @throws SimpleStocksExceptionImpl sse
     */
    @Override
    public double computeDividendYield() throws Exception {
        if (this.getLastDividendValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock's last dividend cannot be negative");

        if (this.getMarketValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock market price cannot be negative");

        if (this.getMarketValue() == ZERO)
            throw new SimpleStocksExceptionImpl("Divide by Zero Error: Stock market price cannot be zero");

         return this.getLastDividendValue() / this.getMarketValue();
    }

    /**
     * Method to clone a common stock
     * @return Stock stock: a copy of an instance of a common stock
     */
    public Stock clone() {
            return (Stock) super.clone();
    }
}
