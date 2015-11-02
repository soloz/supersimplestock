package com.jpmorgan.interviews.codes.client;

import com.jpmorgan.interviews.codes.api.controller.TradeManager;
import com.jpmorgan.interviews.codes.api.client.SuperSimpleStockClient;
import com.jpmorgan.interviews.codes.model.*;
import com.jpmorgan.interviews.codes.controller.*;
import com.jpmorgan.interviews.codes.exception.*;
import com.jpmorgan.interviews.codes.util.StockFactory;
import java.util.regex.Pattern;

import java.util.*;

/**
 * Client to test the functionality of the Super Simple Stock application
 * Created by sadebayo on 28/10/2015.
 */
public class SuperSimpleStockClientImpl implements SuperSimpleStockClient {
    private TradeManager tm = null;
    private static final double ZERO_DOUBLE = 0.0;
    private static final int ZERO_INT = 0;
    private static Scanner scanner;
    private static final String CHARSET = "UTF-8";
    private static final Locale LOCALE = Locale.UK;
    private static final Pattern WHITESPACE = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern ALL = Pattern.compile("\\A");
    private static final Pattern NL = Pattern.compile("\\n");

    public SuperSimpleStockClientImpl(Trade trade){

    }

    /**
     * Client constructor
     * @param TradeManager tm.
     */
    public SuperSimpleStockClientImpl(TradeManager tm){
        this.tm = tm;
    }

    /**
     * Client service method to set TradeManager for client
     * @param TradeManager tm.
     */
    public void setTradeManager(TradeManager tm){
        this.tm = tm;
    }

    /**
     * Function to return TradeManager for client
     * @return TradeManager tm: Manages all stocks and trades.
     */
    public TradeManager getTradeManager(){
        return this.tm;
    }

    /**
     * Client service method to compute the PE ratio for a given stock (symbol)
     * @param String symbol: Stock symbol.
     * @return double PERatio: computed PE Ratio.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computePERatio(String symbol) throws Exception {
        return tm.getStock(symbol).computePERatio();
    }

    /**
     * Client service method to compute the dividend yield for a given stock (symbol)
     * @param String symbol: Stock symbol
     * @return double dividendYield: Computed dividend yield.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeDividendYield(String symbol) throws Exception {
        return tm.getStock(symbol).computeDividendYield();
    }

    /**
     * Client service method to compute GBCE All Shares Index for stocks
     * @return double allShareIndex: Computed GBCE All Share Index for all traded stocks.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeAllSharesIndex() throws Exception {
        return tm.computeAllSharesIndex();
    }

    /**
     * Client service method to compute weighted volume price for a given stock symbol
     * @param List<Trade> trades: A list of trades involving only the given stock -
     *              filtered from all trades the system.
     * @return double weightedVolumestockPrice: Weighted stock price obtained by
     *              volumes of trades involving stock.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeWeightedVolumeStockPrice(List<Trade> trades) throws Exception {
        return tm.computeWeightedVolumeStockPrice(trades);
    }

    /**
     * Client service method to compute weighted volume price for a given stock symbol
     *              within an interval
     * @param String symbol: Stock symbol for which weighted price by volume is to be computed.
     * @param int interval: Trading time interval (in minutes) within which weighted volume
     *              price should be computed for given symbol.
     * @return double weightedVolumestockPrice: Weighted stock price obtained by
     *              volumes of trades involving stock.
     * @throws SimpleStocksExceptionImpl sse
     */
    public double computeWeightedVolumeStockPriceInInterval(String symbol, int interval) throws Exception {
        return tm.computeWeightedVolumeStockPriceInInterval(symbol, interval);
    }

    /**
     * Client service method to return a list of trades in system
     * @return List<Trade> trades: List of trades in system.
     * @throws SimpleStocksExceptionImpl sse
     */
    public List<Trade> getTrades() throws Exception {
        return this.tm.getTrades();
    }

    /**
     * Client service method to record trades
     * @param Trade trade: Trade trade to be recorded.
     * @throws SimpleStocksExceptionImpl sse
     */
    public void recordTrade(Trade trade) throws Exception {
            this.tm.recordTrade(trade);
    }

    /**
     * Client service main method to take user inputs
     * @param String args[]: Command line arguments.
     * @throws SimpleStocksExceptionImpl sse
     */
    public  static  void main(String args[]) throws Exception{
        String symbol = null;
        double price = ZERO_DOUBLE;
        int qty = ZERO_INT;
        TradeType tradeType = TradeType.BUY;
        int selectedOperation;
        int selectedTicker;
        Stock currentStock;

        Map<String, Stock> stocks = new HashMap<String, Stock>();
        StockFactory factory = StockFactory.getInstance();
        TradeManager tm = new TradeManagerImpl(factory.getStocks());
        SuperSimpleStockClientImpl client = new SuperSimpleStockClientImpl(tm);

        printOperationsPrompt();

        while(scanner.hasNext()){

            try{
                if (scanner.hasNextInt()){

                    selectedOperation = scanner.nextInt();

                    switch (selectedOperation){
                        case 1:
                            printAvailableStocks();

                                while(scanner.hasNext()) {
                                        String line = scanner.nextLine();
                                        String [] tokens = tokenizer(line);

                                        if (tokens != null){
                                            if (tokens[0].equals("q"))
                                                break;

                                            symbol = tokens[0];
                                            price = Double.parseDouble(tokens[1]);

                                            currentStock = factory.createStock(symbol);
                                            currentStock.setMarketValue(price);
                                            try{
                                                System.out.println("Dividend Yield for Stock " + symbol + " = " + currentStock.computeDividendYield());
                                            }catch(SimpleStocksExceptionImpl sse){
                                                System.out.println(sse.getMessage());
                                            }
                                        }

                                    printAvailableStocks();
                                }

                            printOperationsPrompt();

                            break;
                        case 2:

                            printAvailableStocks();

                            while(scanner.hasNext()) {
                                String line = scanner.nextLine();
                                String [] tokens = tokenizer(line);

                                if (tokens != null){
                                    if (tokens[0].equals("q"))
                                        break;

                                    symbol = tokens[0];
                                    price = Double.parseDouble(tokens[1]);

                                    currentStock = factory.createStock(symbol);
                                    currentStock.setMarketValue(price);

                                    try{
                                        System.out.println("PE Ratio for Stock " + symbol + " = " + currentStock.computePERatio());
                                    }catch(SimpleStocksExceptionImpl ssse){
                                        System.out.println(ssse.getMessage());
                                    }

                                }

                                printAvailableStocks();
                            }

                            printOperationsPrompt();

                            break;

                        case 3:
                            printAvailableStocks();

                            while(scanner.hasNext()) {
                                String line = scanner.nextLine();
                                String [] tokens = tokenizer(line);

                                if (tokens != null){
                                    if (tokens[0].equals("q"))
                                        break;

                                    symbol = tokens[0];
                                    qty = Integer.parseInt(tokens[1]);
                                    price = Integer.parseInt(tokens[2]);

                                    currentStock = factory.createStock(symbol);
                                    currentStock.setMarketValue(price);

                                    if (tokens[3] == "BUY")
                                        tradeType = TradeType.BUY;
                                    else
                                        tradeType = TradeType.SELL;

                                    try{
                                        client.recordTrade(new Trade(currentStock, qty, tradeType, price));

                                        System.out.println("Recorded trades: " + client.getTrades().size());
                                        System.out.println(client.getTradeManager().getTrades().get(0).getTimeStamp());
                                    }catch(SimpleStocksExceptionImpl sse){
                                        System.out.println(sse.getMessage());
                                    }

                                }

                                printAvailableStocks();
                            }

                            printOperationsPrompt();

                            break;

                        case 4:

                            printAvailableStocks();

                            while(scanner.hasNext()) {
                                String line = scanner.nextLine();
                                String [] tokens = tokenizer(line);

                                if (tokens != null){
                                    if (tokens[0].equals("q"))
                                        break;

                                    symbol = tokens[0];
                                    int interval = Integer.parseInt(tokens[1]);

                                    try {
                                        System.out.println("Weighted Volume Stock Price for " + symbol + ": " + client.computeWeightedVolumeStockPriceInInterval(symbol, interval));
                                        System.out.println("Recorded trades: " + client.getTrades().size());
                                    }catch(SimpleStocksExceptionImpl sse){
                                        System.out.println(sse.getMessage());
                                    }


                                }

                                printAvailableStocks();
                            }

                            printOperationsPrompt();

                            break;

                        case 5:

                            try {
                                System.out.println("GBCE All Shares Index = " + client.computeAllSharesIndex());
                            }catch(SimpleStocksExceptionImpl sse){
                                System.out.println(sse.getMessage());
                            }

                            printOperationsPrompt();
                            break;

                    }
                } else {
                    String entry = scanner.next();

                    if (entry.equals("q"))
                        break;

                    System.out.println("Invalid input: "+ entry);
                    System.out.print("Please choose operation: ");
                }
            }catch (InputMismatchException ime){
                ime.printStackTrace();
            }

        }

    }

    static {
        restart();
    }

    private static void printOperationsPrompt() {
        System.out.println("");
        System.out.println("");

        System.out.println("Permitted Operations");

        for (int i = 0 ; i < 100 ; i++){
            System.out.print("=");
        }
        System.out.println("");

        System.out.println("1. Compute dividend yield");
        System.out.println("2. Compute PERatio");
        System.out.println("3. Trade");
        System.out.println("4. Compute Weighted Volume");
        System.out.println("5. Compute All Share Index");

        System.out.print("Please choose operation: ");
    }

    public static void printAvailableStocks(){
        System.out.println("");
        System.out.println("");

        System.out.println("Available stocks");
        for (int i = 0 ; i < 20 ; i++){
            System.out.print("=");
        }
        System.out.println("");
        System.out.println("TEA");
        System.out.println("POP");
        System.out.println("ALE");
        System.out.println("GIN");
        System.out.println("JOE");

        System.out.print("Please enter ticker and price (separated by space) or exit sub-menu with 'q': ");

    }

    private static String [] tokenizer(String line) {
        String [] tokens = null;
        tokens = WHITESPACE.split(line);
        //tokens = line.trim().split(" ");
        //System.out.println(tokens.length);
        //System.out.println(tokens[1]);

        if (tokens.length == 1){
            if (tokens[0].equals("q")){
                return tokens;
            }
            else{
                return null;
            }
        }
        return tokens;
    }

    private static void restart() {
        setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET));
    }

    private static void setScanner(Scanner scanner) {
        SuperSimpleStockClientImpl.scanner = scanner;
        SuperSimpleStockClientImpl.scanner.useLocale(LOCALE);
    }
}
