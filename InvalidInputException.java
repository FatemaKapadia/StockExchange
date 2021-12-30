package StockExchange;

// Custom exception to check if the entered input is valid
public class InvalidInputException extends Exception {
    public InvalidInputException(String str) {
        super(str);
    }    
}
