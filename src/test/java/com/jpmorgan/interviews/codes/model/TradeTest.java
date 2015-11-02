package com.jpmorgan.interviews.codes.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class TradeTest {
    Stock stock;
    double lastDividendValue;
    double parValue;
    String symbol;
    double price;
    double fixedDividendValue;
    Trade trade;
    TradeType tt;
    int qty;

    @Before
    public void setUp() throws Exception {

        lastDividendValue = 8.0;
        parValue = 100;
        symbol = "GIN";
        price = 300.0;
        fixedDividendValue = 2;
        tt = TradeType.BUY;

        stock = new PreferredStock(lastDividendValue, fixedDividendValue, parValue, symbol);
        trade = new Trade(stock, qty, tt, price);
    }

    @After
    public void tearDown() throws Exception {
        stock = null;
        trade = null;
    }

    @Test
    public void getValueTest() throws Exception {
        //expected result
        double value = qty * price;
        assertEquals(value, trade.getValue(), 0.0);

    }

}