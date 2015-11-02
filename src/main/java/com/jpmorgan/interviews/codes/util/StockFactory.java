package com.jpmorgan.interviews.codes.util;

import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;
import com.jpmorgan.interviews.codes.model.CommonStock;
import com.jpmorgan.interviews.codes.model.PreferredStock;
import com.jpmorgan.interviews.codes.model.Stock;
import java.util.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public class StockFactory {
    private final static Map<String, Stock> stocks = new HashMap<String, Stock>();

    /**
     * Default constructor. Cannot be instantiated.
     */
    private StockFactory(){
        stocks.put("TEA", new CommonStock(0.0, 100.0, "TEA"));
        stocks.put("POP", new CommonStock(8.0, 100.0, "POP"));
        stocks.put("ALE", new CommonStock(23.0, 60.0, "ALE"));
        stocks.put("GIN", new PreferredStock(8.0, 2.0, 100.0, "GIN"));
        stocks.put("JOE", new CommonStock(13.0, 250.0, "JOE"));
    }

    /**
     * Method to create a stock copy for sale or purchase.
     * @params String symbol: Stock symbol.
     * @return Stock stock: Traded stock
     */
    public static Stock createStock(String symbol) throws Exception{
        //System.out.println(stocks.size());
        return stocks.get(symbol).clone();
    }

    /**
     * Method to get stocks to instantiate a trading manager application.
     * @return Map<String, Stock> stocks: Traded stocks
     */
    public static Map<String, Stock> getStocks(){
        return  stocks;
    }

    /**
     * Method to return this factory as a singleton.
     * @return Stockfactory this; Return an instance of this StockFactory
     */
    public static StockFactory getInstance(){
        return new StockFactory();
    }
}
