package com.jpmorgan.interviews.codes.api.controller;

import com.jpmorgan.interviews.codes.model.*;
import java.util.*;

/**
 * Created by sadebayo on 28/10/2015.
 */
public interface TradeManager {

    public void recordTrade(Trade trade) throws Exception;
    public List<Trade> getTrades() throws Exception;
    public void addStock(Stock stock, String symbol) throws Exception;
    public Map<String, Stock> getStocks() throws Exception;
    public Stock getStock(String symbol) throws Exception;
    public double computeWeightedVolumeStockPrice(List<Trade> trades) throws Exception;
    public double computeWeightedVolumeStockPriceInInterval(String symbol, int interval) throws Exception;
    public double computeAllSharesIndex() throws Exception;
    public double computeGeometricMean(List<Double> priceList);
    public List<Trade> filterTrade(List<Trade> trades, String symbol, int interval);
    public List<Trade> filterTrade(List<Trade> trades, int interval);
    public int getNumberOfTrades();
    public int getNumberOfStocks();

}
