package com.jpmorgan.interviews.codes.controller;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;
import com.jpmorgan.interviews.codes.model.*;
import com.jpmorgan.interviews.codes.api.controller.TradeManager;
import com.jpmorgan.interviews.codes.util.StockFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class TradeManagerImplTest {
    Stock stock;
    double lastDividendValue;
    double parValue;
    String symbol1, symbol2, symbol3, symbol4;
    double price1, price2, price3, price4;
    double fixedDividendValue;
    Trade trade1, trade2, trade3, trade4;
    TradeType tt1, tt2, tt3, tt4;
    int qty1, qty2, qty3, qty4;
    TradeManager tm;
    StockFactory factory;
    Map<String, Stock> stocks = null;

    @Before
    public void setUp() throws Exception {
        //trade 1 data
        qty1 = 200;
        price1 = 1000.0;
        symbol1 = "GIN";
        tt1 = TradeType.BUY;

        //trade 2 data
        qty2 = 800;
        price2 = 800.0;
        symbol2 = "ALE";
        tt2 = TradeType.BUY;

        //trade 3 data
        qty3 = 2000;
        price3 = 500.0;
        symbol3 = "POP";
        tt3 = TradeType.SELL;

        //trade 3 data
        qty4 = 1000;
        price4 = 500.0;
        symbol4 = "POP";
        tt4 = TradeType.SELL;

        factory = StockFactory.getInstance();
        trade1 = new Trade(factory.createStock(symbol1), qty1, tt1, price1);
        trade2 = new Trade(factory.createStock(symbol2), qty2, tt2, price2);
        trade3 = new Trade(factory.createStock(symbol3), qty3, tt3, price3);
        trade4 = new Trade(factory.createStock(symbol4), qty4, tt4, price4);

        tm = new TradeManagerImpl(factory.getStocks());
    }

    @After
    public void tearDown() throws Exception {
        stock = null;
        trade1 = null;
        trade2 = null;
        trade3 = null;

        tm = null;
        factory = null;
    }

    @Test
    public void recordTradeTest() throws Exception{
        //expected result
        tm.recordTrade(trade1);

        assertNotNull(tm.getTrades().get(0));
        assertEquals(trade1, tm.getTrades().get(0));
        assertEquals(1, tm.getNumberOfTrades());
    }

    @Test
    public void recordMultipleTradesTest() throws Exception{
        //expected result
        tm.recordTrade(trade2);
        tm.recordTrade(trade3);

        assertNotNull(tm.getTrades().get(0));
        assertNotNull(tm.getTrades().get(1));

        assertEquals(trade2, tm.getTrades().get(0));
        assertEquals(trade3, tm.getTrades().get(1));

        assertEquals(2, tm.getNumberOfTrades());
    }


    @Test
    public void computeWeightedVolumeStockPriceTest() throws Exception{
        //test data
        int volume = 0;
        double stockPriceTotal = 0.0;
        double stockPriceWeighted = 0.0;

        //record trades
        tm.recordTrade(trade1);
        tm.recordTrade(trade2);
        tm.recordTrade(trade3);

        for (Trade trade: tm.getTrades()){
            stockPriceTotal += trade.getPrice() * trade.getQuantity();
            volume += trade.getQuantity();
        }

        stockPriceWeighted = stockPriceTotal / volume;

        assertEquals(tm.computeWeightedVolumeStockPrice(tm.getTrades()), stockPriceWeighted, 0.0);
    }

    @Test
    public void computeTradesWithinIntervalTest() throws Exception{
        //timestamp data;
        long trade1Time = System.currentTimeMillis() - 60000;
        long trade2Time = System.currentTimeMillis() - 110000;
        long trade3Time = System.currentTimeMillis() - 180000;
        long trade4Time = System.currentTimeMillis() - 400000;
        int interval = 2;
        long now = System.currentTimeMillis();

        //expected result: number of trades for symbol POP
        int numberOfTrades = 2;

        //record trades
        tm.recordTrade(trade1);
        trade1.setTimeStamp(trade1Time);

        tm.recordTrade(trade2);
        trade2.setTimeStamp(trade2Time);

        tm.recordTrade(trade3);
        trade3.setTimeStamp(trade3Time);

        tm.recordTrade(trade4);
        trade4.setTimeStamp(trade4Time);

        assertEquals(numberOfTrades, tm.filterTrade(tm.getTrades(), interval).size());
    }

    @Test
    public void computeTradedSymbolsWithinIntervalTest() throws Exception{
        //timestamp data;
        long trade1Time = System.currentTimeMillis() - 6000;
        long trade2Time = System.currentTimeMillis() - 11000;
        long trade3Time = System.currentTimeMillis() - 200000;
        long trade4Time = System.currentTimeMillis() - 40000;
        int interval = 2;
        long now = System.currentTimeMillis();

        //expected result: number of trades for symbol POP
        int numberOfTrades = 1;

        //record trades
        tm.recordTrade(trade1);
        trade1.setTimeStamp(trade1Time);

        tm.recordTrade(trade2);
        trade2.setTimeStamp(trade2Time);

        tm.recordTrade(trade3);
        trade3.setTimeStamp(trade3Time);

        tm.recordTrade(trade4);
        trade4.setTimeStamp(trade4Time);

        assertEquals(numberOfTrades, tm.filterTrade(tm.getTrades(), symbol4, interval).size());
    }

    @Test
    public void computeWeightedVolumeStockPriceInIntervalTest() throws Exception{
        //timestamp data;
        long trade1Time = System.currentTimeMillis() - 6000;
        long trade2Time = System.currentTimeMillis() - 18000;
        long trade3Time = System.currentTimeMillis() - 11000;
        long trade4Time = System.currentTimeMillis() - 10000;
        int interval = 2;
        long now = System.currentTimeMillis();

        //expected result: number of trades for symbol POP
        int numberOfTrades = 2;

        //test data
        int volume1 = 0;
        int volume2 = 0;
        double stockPriceTotal1 = 0.0;
        double stockPriceTotal2 = 0.0;
        double stockPriceWeighted1 = 0.0;
        double stockPriceWeighted2 = 0.0;

        //record trades
        tm.recordTrade(trade1);
        trade1.setTimeStamp(trade1Time);

        tm.recordTrade(trade2);
        trade2.setTimeStamp(trade2Time);

        tm.recordTrade(trade3);
        trade3.setTimeStamp(trade3Time);

        tm.recordTrade(trade4);
        trade4.setTimeStamp(trade4Time);

        //compute weighted stock price for symbol1
        stockPriceTotal1 += trade1.getPrice() * trade1.getQuantity();
        volume1 += trade1.getQuantity();

        stockPriceWeighted1 = stockPriceTotal1 / volume1;

        //compute weighted stock price for symbol4
        System.out.println(trade1.getPrice());
        stockPriceTotal2 += trade3.getPrice() * trade3.getQuantity();
        volume2 += trade3.getQuantity();
        stockPriceTotal2 += trade4.getPrice() * trade4.getQuantity();
        volume2 += trade4.getQuantity();

        stockPriceWeighted2 = stockPriceTotal2 / volume2;

        assertEquals(stockPriceWeighted1, tm.computeWeightedVolumeStockPriceInInterval(symbol1, interval), 0.0);
        assertEquals(stockPriceWeighted2, tm.computeWeightedVolumeStockPriceInInterval(symbol4, interval), 0.0);
    }

    @Test
    public void computeGeometricMeanTest() throws Exception{
        //test data
        List<Double> prices = new ArrayList<Double>();
        prices.add(130.0);
        prices.add(670.0);
        prices.add(150.0);

        double prod = 1.0;
        double power = 1 / (double) prices.size();

        for (int i = 0 ; i < prices.size(); i++){
            prod  = prod * prices.get(i);
        }

        double outcome = Math.pow(prod, power);

        assertEquals(outcome, tm.computeGeometricMean(prices), 0.0);
    }

    @Test
    public void computeAllShareIndexTest() throws Exception{
        List<Double> prices = new ArrayList<Double>();

        //record trades
        tm.recordTrade(trade1);
        tm.recordTrade(trade2);
        tm.recordTrade(trade3);
        tm.recordTrade(trade4);

        prices.add(tm.computeWeightedVolumeStockPriceInInterval(trade1.getStock().getSymbol(),0));
        prices.add(tm.computeWeightedVolumeStockPriceInInterval(trade2.getStock().getSymbol(),0));
        prices.add(tm.computeWeightedVolumeStockPriceInInterval(trade3.getStock().getSymbol(),0));

        double prod = 1.0;
        double power = 1 / (double) prices.size();

        for (int i = 0 ; i < prices.size(); i++){
            prod  = prod * prices.get(i);
        }

        double outcome = Math.pow(prod, power);

        assertEquals(outcome, tm.computeAllSharesIndex(), 0.0);
    }

    /*
    @Test
    public void addStockTest() throws Exception{
        //expected result
        tm.addStock(factory.createStock("GNN"), "GNN");
    }
    */

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void recordTradeException1Test() throws Exception{
        //expected result
        Trade trade = null;
        tm.recordTrade(trade);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void addStockExceptionNullStockTest() throws Exception{
        //expected result
        tm.addStock(null, "GIN");
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void addStockExceptionNullSymbolTest() throws Exception{
        //expected result
        tm.addStock(factory.createStock("GIN"), null);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void addStockExceptionExistingStockTest() throws Exception{
        //expected result
        tm.addStock(factory.createStock("GIN"), "GIN");
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void getStockExceptionTest() throws Exception{
        //expected result
        tm.getStock("GNN");
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeWeightedVolumeStockPriceExceptionTest() throws Exception{
        int qty = 0;
        trade3 = new Trade(factory.createStock(symbol4), qty, tt1, price3);
        trade4 = new Trade(factory.createStock(symbol4), qty, tt2, price4);

        tm.recordTrade(trade3);
        tm.recordTrade(trade4);

        tm.computeWeightedVolumeStockPriceInInterval(symbol4, 0);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeWeightedVolumeStockPriceInIntervalException1Test() throws Exception{
        tm.computeWeightedVolumeStockPriceInInterval(null, 0);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeWeightedVolumeStockPriceInIntervalException2HigherTest() throws Exception{
        int qty = 0;
        trade3 = new Trade(factory.createStock(symbol4), qty, tt1, price3);
        trade4 = new Trade(factory.createStock(symbol4), qty, tt2, price4);

        tm.recordTrade(trade3);
        tm.recordTrade(trade4);

        tm.computeWeightedVolumeStockPriceInInterval(symbol4, Integer.MAX_VALUE + 1);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeWeightedVolumeStockPriceInIntervalException2LowerTest() throws Exception{
        int qty = 0;
        trade3 = new Trade(factory.createStock(symbol4), qty, tt1, price3);
        trade4 = new Trade(factory.createStock(symbol4), qty, tt2, price4);

        tm.recordTrade(trade3);
        tm.recordTrade(trade4);

        tm.computeWeightedVolumeStockPriceInInterval(symbol4, -2);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeWeightedVolumeStockPriceInIntervalException3Test() throws Exception{
        //timestamp data;
        long trade1Time = System.currentTimeMillis() - 60000;
        long trade2Time = System.currentTimeMillis() - 180000;
        long trade3Time = System.currentTimeMillis() - 510000;
        long trade4Time = System.currentTimeMillis() - 200000;
        int interval = 2;

        //test data
        int volume1 = 0;
        int volume2 = 0;
        double stockPriceTotal1 = 0.0;
        double stockPriceTotal2 = 0.0;
        double stockPriceWeighted1 = 0.0;
        double stockPriceWeighted2 = 0.0;

        //record trades
        tm.recordTrade(trade1);
        trade1.setTimeStamp(trade1Time);

        tm.recordTrade(trade2);
        trade2.setTimeStamp(trade2Time);

        tm.recordTrade(trade3);
        trade3.setTimeStamp(trade3Time);

        tm.recordTrade(trade4);
        trade4.setTimeStamp(trade4Time);

        tm.computeWeightedVolumeStockPriceInInterval(symbol4, interval);
    }

    @Test(expected = SimpleStocksExceptionImpl.class)
    public void computeAllShareIndexException1Test() throws Exception{
        List<Double> prices = new ArrayList<Double>();
        tm.computeAllSharesIndex();
    }

}