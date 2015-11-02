package com.jpmorgan.interviews.codes.controller;

import com.jpmorgan.interviews.codes.api.controller.TradeManager;
import com.jpmorgan.interviews.codes.exception.SimpleStocksExceptionImpl;
import com.jpmorgan.interviews.codes.model.Stock;
import com.jpmorgan.interviews.codes.model.Trade;
import com.jpmorgan.interviews.codes.util.DateTimeUtil;

import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Trade manager to manage stocks and trading on the Super Simple Stock trading application
 * Created by sadebayo on 28/10/2015.
 */
public class TradeManagerImpl implements TradeManager {
    Map<String, Stock> stocks = null;
    List<Trade> trades = null;
    private static final double ZERO_DOUBLE = 0.0;
    private static final int ZERO_INT = 0;
    private DateTimeUtil timeUtil;

    /**
     * Constructor TradeManager implementation
     * @params Map<String, Stock> stocks.
     * @params List<Trade> trades.
     */
    public TradeManagerImpl(Map<String, Stock> stocks, List<Trade> trades){
        this.stocks = stocks;
        this.trades = trades;
    }

    /**
     * Constructor for TradeManager implementation.
     * Initialises default (empty) trades.
     * @params Map<String, Stock> stocks.
     */
    public TradeManagerImpl(Map<String, Stock> stocks){
        this.stocks = stocks;
        this.trades = new ArrayList<Trade>();
    }

    /**
     * Constructor for TradeManager implementation
     * Initialises default (empty) stocks and trades.
     */
    public TradeManagerImpl(){
        this.stocks = new HashMap<String, Stock>();
        this.trades = new ArrayList<Trade>();
    }

    /**
     * Method to set stocks available for trading
     * @params Map<String, Stock> stocks : Stocks to be made available for trading
     * @throws SimpleStocksExceptionImpl sse
     */
    public void setStocks (Map<String, Stock> stocks){
        this.stocks = stocks;
    }

    /**
     * Method to set trades generated from an external system into manager
     * @params List<Trade> trades : Trades conducted from external system
     * @throws SimpleStocksExceptionImpl sse
     */
    public void setTrades (List<Trade> trades){
        this.trades = trades;
    }

    /**
     * Method to record ongoing trade into manager
     * @params Trade trade : Trade to record in system
     * @throws SimpleStocksExceptionImpl sse
     */
    public void recordTrade(Trade trade) throws Exception {
        if (trade == null)
            throw new SimpleStocksExceptionImpl("[StockTradeManager] Trade cannot be null.");

        trade.setTimeStamp(System.currentTimeMillis());

        if (!this.trades.add(trade)){
            throw new SimpleStocksExceptionImpl("[StockTradeManager] Unable to record trade into system");
        }
    }

    /**
     * Method to return trades in system
     * @return List<Trade> trades : Trades in system
     * @throws SimpleStocksExceptionImpl sse
     */
    public List<Trade> getTrades() throws Exception {
        return this.trades;
    }

    /**
     * Method to add stock to the system, indexed by stock symbol
     * @params Stock stock : Stock to add to system for trading
     * @params String symbol : Symbol of stock to add to system (not needed) TODO: remove in version 0.02
     * @throws SimpleStocksExceptionImpl sse
     */
    public void addStock(Stock stock, String symbol) throws Exception {
        if (stock == null)
            throw new SimpleStocksExceptionImpl("Stock cannot be null");

        if (symbol == null)
            throw new SimpleStocksExceptionImpl("Symbol cannot be null");

        if (this.stocks.containsKey(symbol))
            throw new SimpleStocksExceptionImpl("Stock already exists in the stock list");

        this.stocks.put(symbol, stock);
    }

    /**
     * Method to return stocks traded in system
     * @return Map<String, Stock> stocks : Stocks traded in system
     * @throws SimpleStocksExceptionImpl sse
     */
    public Map<String, Stock> getStocks() throws Exception {
        return this.stocks;
    }

    /**
     * Method to return a stock given symbol
     * @params String symbol : Stock symbol index for stock to return
     * @return Stock stock : Stock returned
     * @throws SimpleStocksExceptionImpl sse
     */
    public Stock getStock(String symbol) throws Exception {
        Stock stock = null;

        if (symbol == null)
            throw new SimpleStocksExceptionImpl(("Stock symbol cannot be null."));

        if (this.stocks.containsKey(symbol)){
            stock = this.stocks.get(symbol);
        }else{
            throw new SimpleStocksExceptionImpl("Stock does not exist");
        }

        return stock;
    }

    /**
     * Method to compute weighted volume price for a given stock symbol
     * @params List<Trade> trades: A list of trades involving only the given stock -
     *              filtered from all trades the system.
     * @return double weightedVolumestockPrice: Weighted stock price obtained by
     *              volumes of trades involving stock.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeWeightedVolumeStockPrice(List<Trade> trades) throws Exception {
        double stockPriceTotal = ZERO_DOUBLE;
        double stockPriceWeighted = ZERO_DOUBLE;
        int volume = ZERO_INT;

        for (Trade trade: trades){
            stockPriceTotal += trade.getPrice() * trade.getQuantity();
            volume += trade.getQuantity();
        }

        if (volume == ZERO_INT)
            throw new SimpleStocksExceptionImpl("[StockTradeManager] Divide By Zero: No stock found in database for symbol");

        stockPriceWeighted = stockPriceTotal / volume;

        return stockPriceWeighted;
    }

    /**
     * Method to compute weighted volume price for a given stock symbol
     *              within an interval
     * @params String symbol: Stock symbol for which weighted price by volume is to be computed.
     * @params int interval: Trading time interval (in minutes) within which weighted volume
     *              price should be computed for given symbol.
     * @return double weightedVolumestockPrice: Weighted stock price obtained by
     *              volumes of trades involving stock.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeWeightedVolumeStockPriceInInterval(String symbol, int interval) throws Exception {
        List<Trade> stockTrades = new ArrayList<Trade>();

        if (this.getStock(symbol) == null)
            throw new SimpleStocksExceptionImpl("[StockTradeManager] Entity '"+ symbol + "' does not exist");

        if (interval < -1 || interval > Long.MAX_VALUE)
            throw new SimpleStocksExceptionImpl("[StockTradeManager]  Invalid interval specified");

        stockTrades = filterTrade(this.getTrades(), symbol, interval);

        if (stockTrades.size() == 0)
            throw new SimpleStocksExceptionImpl("[StockTradeManager] No trade for stock " + symbol + " was done in the last "+ interval + " minutes");

        System.out.println(stockTrades.size() + " trades exist in the system for " + symbol);
        System.out.println("Last trading for " + symbol + " occured " + DateTimeUtil.getElapsed(stockTrades.get(stockTrades.size() - 1).getTimeStamp()) + " minutes ago");

        return computeWeightedVolumeStockPrice(stockTrades);

    }

    /**
     * Filtering method to filter trades by symbol and interval of trading
     * @params String symbol: Stock symbol for which weighted price by volume is to be computed.
     * @params int interval: Trading time interval (in minutes) within which weighted volume
     *              price should be computed for a given symbol.
     * @return List<Trade> trades: Filtered trades.
     * @throws SimpleStocksExceptionImpl sse
     */
    public List<Trade> filterTrade(List<Trade> trades, String symbol, int interval){
        List<Trade> filteredTrades = new ArrayList<Trade>();

        if (interval > 0){
            //get collection of trades in specified interval
            for (Trade trade: trades){
                if (trade.getStock().getSymbol().equals(symbol) && DateTimeUtil.withinInterval(trade.getTimeStamp(), interval)){
                    filteredTrades.add(trade);
                }
            }
        }else if (interval == 0 || interval == -1){
            for (Trade trade: trades){
                if (trade.getStock().getSymbol().equals(symbol)){
                    filteredTrades.add(trade);
                }
            }
        }

        return  filteredTrades;
    }

    /**
     * Filtering method to filter trades by interval of trading
     * @params  List<Trade> trades: Trade to be filtered.
     * @return  List<Trade> trades: Filtered trades.
     * @throws  SimpleStocksExceptionImpl sse
     */
    public List<Trade> filterTrade(List<Trade> trades, int interval){
        List<Trade> filteredTrades = new ArrayList<Trade>();

        if (interval > 0){
            //get collection of trades in specified interval
            for (Trade trade: trades){
                if (DateTimeUtil.withinInterval(trade.getTimeStamp(), interval)){
                    filteredTrades.add(trade);
                }
            }
        }else if (interval == 0 || interval == -1){
           return trades;
        }

        return  filteredTrades;
    }

    /**
     * Method to compute GBCE All Shares Index for stocks
     * @return double allShareIndex: Computed GBCE All Share Index for all traded stocks.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeAllSharesIndex() throws Exception {
        List<Double> priceList = new ArrayList<Double>();

        for (String symbol: this.stocks.keySet()){
            try{
                priceList.add(this.computeWeightedVolumeStockPriceInInterval(symbol, ZERO_INT));
            }catch (SimpleStocksExceptionImpl sse){
                //System.out.println(sse.getMessage());
            }
        }

        if (priceList == null || priceList.size() < 1)
            throw new SimpleStocksExceptionImpl("[StockTradeManager] No stocks found for GBCE index");

        System.out.println("GBCE index for " + priceList.size() + " stock(s)");

        return computeGeometricMean(priceList);
    }

    /**
     * Method to compute and return geometric mean of price values
     * @params List<Double> prices: Price list for computing geometric means
     * @return double geoMean: geometric mean for price list
     */
    public double computeGeometricMean(List<Double> priceList) {

        double prod = 1.0;
        double power = 1 / (double) priceList.size();

        for (int i = 0 ; i < priceList.size(); i++){
            prod  = prod * priceList.get(i);

        }
        System.out.println(prod);
        System.out.println(power);
        return Math.pow(prod, power);
    }

    public int getNumberOfTrades(){
        return this.trades.size();
    }

    public int getNumberOfStocks(){
        return this.stocks.size();
    }
}
