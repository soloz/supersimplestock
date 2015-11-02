package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class CommonStockTest {
    Stock stock;
    double lastDividendValue;
    double parValue;
    String symbol;
    double price;

    @Before
    public void setUp() throws Exception {
        lastDividendValue = 8.0;
        parValue = 100;
        symbol = "POP";
        price = 200.0;

        stock = new CommonStock(lastDividendValue, parValue, symbol);

    }

    @After
    public void tearDown() throws Exception {
        stock = null;
    }

    @Test
    public void stockPropertiesTest() throws Exception{
        String sym = "POP";
        double par = 100;
        double last = 8.0;

        assertEquals(sym, stock.getSymbol());
        assertEquals(par, stock.getParValue(), 0.0);
        assertEquals(last, stock.getLastDividendValue(), 0.0);
    }

    @Test
    public void computeDividendYieldTest() throws Exception{
        //expected result
        double expectedDividendYield  =  lastDividendValue / price ;

        assertEquals(expectedDividendYield, stock.computeDividendYield(price), 0.0);
    }

    @Test
    public void computePERatioTest() throws Exception{
        //expected result
        double expectedPERatio  =  price / stock.computeDividendYield(price);

        assertEquals(expectedPERatio, stock.computePERatio(), 0.0);
    }

    //Testing for exception due to negative value for lastDividendValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException1Test() throws Exception{
        double negativeNastDividendValue = -1.0;
        stock = new CommonStock(negativeNastDividendValue, parValue, symbol);
       stock.computeDividendYield(price);
    }

    //Testing for exception due to negative value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException2Test() throws Exception{
        double negativePrice = -100.0;
        stock.computeDividendYield(negativePrice);
    }

    //Testing for exception due to 0 value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException3Test() throws Exception{
        double zeroPrice = 0.0;
        stock.computeDividendYield(zeroPrice);
    }

    //Testing for computerPERatio exception due to negative value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computePERatioException1Test() throws Exception{
        double negativePrice = -100.0;
        stock.computePERatio(negativePrice);
    }

    //Testing for computerPERatio exception due to negative value for dividendYield
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computePERatioException2Test() throws Exception{
        //test data
        price = 100.0;
        symbol = "TEA";
        lastDividendValue = 0;
        parValue = 100.0;

        stock = new CommonStock(lastDividendValue, parValue, symbol);

        stock.computeDividendYield(price);
        stock.computePERatio(price);
    }

}