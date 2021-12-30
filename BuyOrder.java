package StockExchange;

// Each order can be of type buy or sell and object of this can be created only
public class BuyOrder extends Order {
    public BuyOrder(String name, int prc, String tradingParty, String typ) {
        stockName = name;
        price = prc;
        tradingPartyName = tradingParty;
        type = typ;
    }
}
