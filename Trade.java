package StockExchange;

public class Trade {

    private int price;
    private String stockName, buyerTradingPartyName, sellerTradingPartyName;

    public Trade(SellOrder sell, BuyOrder buy) {
        price = (sell.getPrice() + buy.getPrice()) / 2;
        stockName = buy.getStockName();
        buyerTradingPartyName = buy.getTradingPartyName();
        sellerTradingPartyName = sell.getTradingPartyName();
    }

    public int getPrice() {
        return price;
    }

    public String getStockName() {
        return stockName;
    }

    public String getBuyerTradingPartyName() {
        return buyerTradingPartyName;
    }

    public String getSellerTradingPartyName() {
        return sellerTradingPartyName;
    }

}
