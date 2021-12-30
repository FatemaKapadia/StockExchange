package StockExchange;

public class Quote {
    private String stockName;
    private int bestBuyPrice, bestSellPrice;

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
}
