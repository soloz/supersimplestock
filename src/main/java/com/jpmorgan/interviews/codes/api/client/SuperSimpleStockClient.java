package com.jpmorgan.interviews.codes.api.client;

import com.jpmorgan.interviews.codes.model.*;

import java.util.List;


/**
 * Created by sadebayo on 28/10/2015.
 */
public interface SuperSimpleStockClient {

    public double computePERatio(String symbol) throws Exception;
    public double computeDividendYield(String symbol) throws Exception;
    public double computeAllSharesIndex() throws Exception;
    public double computeWeightedVolumeStockPrice(List<Trade> trades) throws Exception;
    public double computeWeightedVolumeStockPriceInInterval(String symbol, int interval) throws Exception;
    public void recordTrade(Trade trade) throws Exception;

}
