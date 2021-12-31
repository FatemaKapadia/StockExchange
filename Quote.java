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
    }

    public boolean updateBuyPrice(int value) {
        // returns true if the quote changes for a particular company
        if (value > bestBuyPrice) {
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

    public void addSellOrder(SellOrder so) {
        sellOrders.add(so);
    }

    public void addBuyOrder(BuyOrder bo) {
        buyOrders.add(bo);
    }

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
