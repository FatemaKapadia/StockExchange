package StockExchange;

import java.util.*;
import java.io.*;

public class StockExchange {
    private String name;
    private Map<String, Quote> registeredStocks;

    // constructor to instantiate new stock exchange
    public StockExchange(String str) {
        name = str;
        registeredStocks = new HashMap<String, Quote>();
    }

    // method called from main class to start execution
    public void start() {
        // Creating a File object for directory
        File directoryPath = new File("D:\\UserFatema\\Java\\StockExchange\\InputFiles");
        // List of all files and directories
        String inputFilePaths[] = directoryPath.list();

        for (String inputFile : inputFilePaths) {
            Runnable r = new FileHandler(inputFile, this);
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
}