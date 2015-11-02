package com.jpmorgan.interviews.codes.model;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class PreferredStockTest {
    Stock stock;
    double lastDividendValue;
    double parValue;
    String symbol;
    double price;
    double fixedDividendValue;

    @Before
    public void setUp() throws Exception {

        lastDividendValue = 8.0;
        parValue = 100;
        symbol = "GIN";
        price = 300.0;
        fixedDividendValue = 2;
        stock = new PreferredStock(lastDividendValue, fixedDividendValue, parValue, symbol);

    }

    @After
    public void tearDown() throws Exception {
        stock = null;
    }

    @Test
    public void stockPropertiesTest() throws Exception{
        String sym = "GIN";
        double par = 100;
        double last = 8.0;

        assertEquals(sym, stock.getSymbol());
        assertEquals(par, stock.getParValue(), 0.0);
        assertEquals(last, stock.getLastDividendValue(), 0.0);
    }

    @Test
    public void computeDividendYieldTest() throws Exception{
        //expected result
        double expectedDividendYield  =  fixedDividendValue * parValue / price ;
        assertEquals(expectedDividendYield, stock.computeDividendYield(price), 0.0);
    }

    @Test
    public void computePERatioTest() throws Exception{

        //expected result
        double expectedPERatio  =  price / stock.computeDividendYield(price);
       assertEquals(expectedPERatio, stock.computePERatio(), 0.0);

    }

    //Testing for exception due to negative value for fixedDividendValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException1Test() throws Exception{
        double negativeFixedDividendValue = -1.0;
        stock = new PreferredStock(lastDividendValue, negativeFixedDividendValue, parValue, symbol);
        stock.computeDividendYield(price);
    }

    //Testing for exception due to zero value for fixedDividendValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException2Test() throws Exception{
        double zeroFixedDividendValue = 0.0;
        stock = new PreferredStock(lastDividendValue, zeroFixedDividendValue, parValue, symbol);
        stock.computeDividendYield(price);
    }

    //Testing for exception due to negative value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException3Test() throws Exception{
        double negativePrice = -100.0;
        stock.computeDividendYield(negativePrice);
    }

    //Testing for exception due to 0 value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException4Test() throws Exception{
        double zeroPrice = 0.0;
        stock.computeDividendYield(zeroPrice);
    }


    //Testing for exception due to 0 value for parValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException5Test() throws Exception{
        parValue = 0.0;
        stock.setParValue(parValue);
        stock.computeDividendYield(price);
    }

    //Testing for exception due to negative value for parValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeDividendYieldException6Test() throws Exception{
        parValue = -1.0;
        stock.setParValue(parValue);
        stock.computeDividendYield(price);
    }

    //Testing for computerPERatio exception due to negative value for marketValue
    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computePERatioException1Test() throws Exception{
        double negativePrice = -100.0;
        stock.computePERatio(negativePrice);
    }

}