package StockExchange;

// This class is abstract because we don't want to instantiate it
public abstract class Order {
    
    // Properties that are common to both buy and sell orders
    protected String stockName, tradingPartyName, type;
    protected int price;    

    public String getStockName(){
        return stockName;
    }

    public String getTradingPartyName(){
        return tradingPartyName;
    }

    public String getType(){
        return type;
    }

    public int getPrice(){
        return price;
    }
}
