package StockExchange;

import java.util.*;
import java.io.*;

public class StockExchange {
    private String name;
    private Map<String, Quote> registeredStocks;
    private final String homeDirectory = "D:\\UserFatema\\Java\\StockExchange\\InputFiles";

    // constructor to instantiate new stock exchange
    public StockExchange(String str) {
        name = str;
        registeredStocks = new HashMap<String, Quote>();
    }

    // method called from main class to start execution
    public void start() {
        // Creating a File object for directory
        File directoryPath = new File(homeDirectory);
        // List of all files and directories
        String inputFilePaths[] = directoryPath.list();

        for (String inputFile : inputFilePaths) {
            Runnable r = new FileHandler(homeDirectory + "\\" + inputFile, this);
            new Thread(r).start();
        }
    }

    // method that returns Quote object based on the name
    public Quote getQuote(String companyName) {
        return registeredStocks.get(companyName);
    }

    // returns true if a new stock is registered
    public void isStockRegistered(String companyName) {
        if (registeredStocks.get(companyName) == null) {
            Quote newStock = new Quote(companyName);
            registeredStocks.put(companyName, newStock);
        }
    }

    // function to write to a common output text file
    public void writeToFile(Quote updatedQuote) {
        File file = new File("D:\\UserFatema\\Java\\StockExchange\\output.txt");
        try {
            // We use BufferedWriter as a wrapper class around FileWriter
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            String str = updatedQuote.getStockName() + " " + updatedQuote.getBestBuyPrice() + " "
                    + updatedQuote.getBestSellPrice() + "\n";
            bw.write(str);
            System.out.println(str);
            bw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public void isTradePossible(Quote stock) {
        ArrayList<SellOrder> sellOrders = stock.getSellOrdersList();
        ArrayList<BuyOrder> buyOrders = stock.getBuyOrdersList();
        ArrayList<SellOrder> sellOrdersToRemove = new ArrayList<SellOrder>();
        for (SellOrder sell : sellOrders) {
            if (buyOrders.size() > 0) {
                BuyOrder buy = buyOrders.get(0);
                if (sell.getType().equals("Market")) {
                    try {
                        areTradingPartiesDifferent(buy, sell);
                        tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                    } catch (TradeNotPossibleException e) {
                        System.out.println(e.getMessage().toString());
                    }
                } else if (sell.getType().equals("IOC")) {
                    if (buy.getType().equals("Market")) {
                        try {
                            areTradingPartiesDifferent(buy, sell);
                            tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                        } catch (TradeNotPossibleException e) {
                            System.out.println(e.getMessage().toString());
                        }
                    } else if (buy.getType().equals("IOC")) {
                        if (buy.getPrice() < sell.getPrice()) {
                            buyOrders.remove(buy);
                            sellOrders.remove(sell);
                        } else {
                            try {
                                areTradingPartiesDifferent(buy, sell);
                                tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                            } catch (TradeNotPossibleException e) {
                                System.out.println(e.getMessage().toString());
                            }
                        }
                    } else {
                        if (buy.getPrice() < sell.getPrice()) {
                            sellOrders.remove(sell);
                        } else {
                            try {
                                areTradingPartiesDifferent(buy, sell);
                                tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                            } catch (TradeNotPossibleException e) {
                                System.out.println(e.getMessage().toString());
                            }
                        }
                    }

                } else {
                    if (buy.getType().equals("Market")) {
                        try {
                            areTradingPartiesDifferent(buy, sell);
                            tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                        } catch (TradeNotPossibleException e) {
                            System.out.println(e.getMessage().toString());
                        }
                    } else if (buy.getType().equals("IOC")) {
                        if (buy.getPrice() < sell.getPrice()) {
                            buyOrders.remove(buy);
                        } else {
                            try {
                                areTradingPartiesDifferent(buy, sell);
                                tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                            } catch (TradeNotPossibleException e) {
                                System.out.println(e.getMessage().toString());
                            }
                        }
                    } else {
                        if (buy.getPrice() >= sell.getPrice()) {
                            try {
                                areTradingPartiesDifferent(buy, sell);
                                tradeSuccessful(sell, buy, sellOrders, buyOrders, sellOrdersToRemove);
                            } catch (TradeNotPossibleException e) {
                                System.out.println(e.getMessage().toString());
                            }
                        }
                    }
                }
            }
        }
        sellOrders.removeAll(sellOrdersToRemove);
    }

    private void areTradingPartiesDifferent(BuyOrder buy, SellOrder sell) throws TradeNotPossibleException {
        if (buy.getTradingPartyName() == sell.getTradingPartyName()) {
            throw new TradeNotPossibleException("The buying and selling parties are the same");
        }
    }

    private void tradeSuccessful(SellOrder sell, BuyOrder buy, ArrayList<SellOrder> sellOrders,
            ArrayList<BuyOrder> buyOrders, ArrayList<SellOrder> sellOrdersToRemove) {
        System.out.println("Trading stock: " + sell.getStockName());
        System.out.println("Parties involved: ");
        System.out.println(sell.getTradingPartyName() + " " + sell.getPrice());
        System.out.println(buy.getTradingPartyName() + " " + buy.getPrice());
        sellOrdersToRemove.add(sell);
        buyOrders.remove(buy);
    }
}