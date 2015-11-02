package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;

/**
 * Preferred stock class, extends abstract class Stock.
 * Created by sadebayo on 28/10/2015.
 */
public class PreferredStock extends Stock{
    private final double ZERO = 0.0;

    private String symbol;
    private double dividendYield;
    private double fixedDividendValue = ZERO;

    /**
     * Default contructor for Preferred stock.
     */
    public PreferredStock(){
        super();
    }

    /**
     * Preferred Stock constructor to initialise stock properties.
     * @params double lastDividendValue: Last dividend value for stock.
     *         double fixedDividendValue: Fixed dividend value
     *         double parValue
     *         String symbol: Stock symbol
     */
    public PreferredStock(double lastDividendValue, double fixedDividendValue, double parValue, String symbol){
        super(lastDividendValue, parValue, symbol);
        this.fixedDividendValue = fixedDividendValue;
    }

    /**
     * Method to compute dividend yield for a common stock
     * @return double dividendYield : Yield in dividend for this stock.
     *          It requires that market price is pre-set
     * @throws SimpleStocksExceptionImpl sse
     */
    @Override
    public double computeDividendYield() throws Exception {
        if (this.getFixedDividendValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock's last dividend cannot be negative");

        if (this.getFixedDividendValue() == ZERO)
            throw new SimpleStocksExceptionImpl("Fixed Dividend Value cannot be zero");

        if (this.getParValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock's Par value cannot be negative");

        if (this.getParValue() == ZERO)
            throw new SimpleStocksExceptionImpl("Par Value cannot be zero");

        if (this.getMarketValue() < ZERO)
            throw new SimpleStocksExceptionImpl("Negative Error: Stock market price cannot be negative");

        if (this.getMarketValue() == ZERO)
            throw new SimpleStocksExceptionImpl("Divide by Zero Error: Stock market price cannot be zero");

        return (this.getFixedDividendValue() * this.getParValue() ) / this.getMarketValue();
    }

    /**
     * Method to set fixed dividend value for stock.
     * @params double  fixedDividendValue: Fixed dividend value for stock.
     */
    public void setFixedDividendValue(double fixedDividendValue) {
        this.fixedDividendValue = fixedDividendValue;
    }

    /**
     * Method to get fixed dividend value for stock.
     * @return double  fixedDividendValue: Fixed dividend value for stock.
     */
    public double getFixedDividendValue() {
        return this.fixedDividendValue;
    }

    /**
     * Method to clone a preferred stock instance.
     * @return Stock  stock: A copy of preferred stock instance.
     */
    public Stock clone() {
            return (Stock) super.clone();
    }
}
