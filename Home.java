package StockExchange;

public class Home {
    public static void main(String[] args){
        StockExchange BSE = new StockExchange("Bombay Stock Exchange");
        BSE.start();
    }
}