package StockExchange;

import java.util.*;

public class Quote {
    private String stockName;
    private int bestBuyPrice, bestSellPrice;
    private ArrayList<SellOrder> sellOrders;
    private ArrayList<BuyOrder> buyOrders;

    public Quote(String name) {
        stockName = name;
        bestBuyPrice = 0;
        bestSellPrice = 0;
        sellOrders = new ArrayList<SellOrder>();
        buyOrders = new ArrayList<BuyOrder>();
    }

    public boolean updateBuyPrice(int value) {
        // returns true if the quote changes for a particular company
        if (value < bestBuyPrice) {
            bestBuyPrice = value;
            return true;
        }
        return false;
    }

    public boolean updateSellPrice(int value) {
        // returns true if the quote changes for a particular company
        if (value > bestSellPrice) {
            bestSellPrice = value;
            return true;
        }
        return false;
    }

    // getter functions for private data members
    public String getStockName() {
        return stockName;
    }

    public int getBestBuyPrice() {
        return bestBuyPrice;
    }

    public int getBestSellPrice() {
        return bestSellPrice;
    }

    public ArrayList<SellOrder> getSellOrdersList() {
        return sellOrders;
    }

    public ArrayList<BuyOrder> getBuyOrdersList() {
        return buyOrders;
    }

    public void addSellOrder(SellOrder so) {
        sellOrders.add(so);
    }

    public void addBuyOrder(BuyOrder bo) {
        buyOrders.add(bo);
    }

    // while sorting sell order list orders with lowest price should be processed
    // first
    public void sortSellOrderList() {
        sellOrders.sort((order1, order2) -> {
            if (order1.getType().equals("Market")) {
                return 1;
            } else if (order2.getType().equals("Market")) {
                return -1;
            } else if (order1.getPrice() < order2.getPrice()) {
                return 1;
            } else if (order1.getPrice() > order2.getPrice()) {
                return -1;
            } else if (order1.getType().equals("IOC")) {
                return 1;
            }
            return -1;
        });
    }

    // while sorting buy order list orders with higher price should be processed
    // first
    public void sortBuyOrderList() {
        buyOrders.sort((order1, order2) -> {
            if (order1.getType().equals("Market")) {
                return 1;
            } else if (order2.getType().equals("Market")) {
                return -1;
            } else if (order1.getPrice() > order2.getPrice()) {
                return 1;
            } else if (order1.getPrice() < order2.getPrice()) {
                return -1;
            } else if (order1.getType().equals("IOC")) {
                return 1;
            }
            return -1;
        });
    }
}
