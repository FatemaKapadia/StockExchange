package StockExchange;

import java.io.*;
import java.util.*;

public class FileHandler implements Runnable {
    // file path that this particular thread will read from
    private String filePath;
    // reference to stock exchange needed by every thread
    private StockExchange exchange;

    public FileHandler(String fp, StockExchange se) {
        filePath = fp;
        exchange = se;
    }

    public void run() {
        // input receives an ArrayList of all the valid instructions and then interprets
        // each one of them
        ArrayList<String> input = readFile();
        for (String instruction : input) {
            String[] words = instruction.split("\\s+");

            // determines which instruction based on the first word of the line
            if (words[0].equals("Sleep")) {
                goToSleep(Integer.parseInt(words[1]));
            } else {
                interpretOrder(words);
            }
        }

    }

    // reads instructions from an input file and also validates it
    public ArrayList<String> readFile() {
        File file = new File(filePath);
        ArrayList<String> input = new ArrayList<String>();
        try {
            // We use BufferedReader as a wrapper class around FileReader
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            // Condition holds true till there is character in a string
            while ((line = br.readLine()) != null) {
                try {
                    validateInput(line);
                } catch (InvalidInputException e) {
                    System.out.println(e);
                }
                input.add(line);
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
        return input;
    }

    // function that makes sure that input format is correct
    private void validateInput(String line) throws InvalidInputException {
        String[] words = line.split("\\s+");

        if (words[0].equals("Sleep")) {
            try {
                Integer.parseInt(words[1]);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage().toString());
            }
        } else {
            try {
                Integer.parseInt(words[1]);
                // conditions to make sure that the instruction is in the correct format
                if (!words[3].equals("Buy") && !words[3].equals("Sell")) {
                    throw new InvalidInputException("Order direction can only be Buy or Sell type");
                }

                if (!words[4].equals("Limit") && !words[4].equals("IOC") && !words[4].equals("Market")) {
                    throw new InvalidInputException("Order type must be Limit or IOC or Market");
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage().toString());
            }
        }

    }

    // method to handle sleep instructions
    private void goToSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage().toString());
        }
    }

    // method to handle order instructions
    private void interpretOrder(String[] instruction) {
        // in case of order instruction, splits the instruction and identify different
        // parts
        String companyName = instruction[0];
        int price = Integer.parseInt(instruction[1]);
        String tradingPartyName = instruction[2];
        String direction = instruction[3];
        String type = instruction[4];

        // this variable determines if the quote has been changed
        boolean isQuoteUpdated = false;

        // in case company is not registered, create a new entry in the HashMap
        exchange.isStockRegistered(companyName);
        Quote currentCompany = exchange.getQuote(companyName);

        if (direction.equals("Buy")) {
            BuyOrder newBuyOrder = new BuyOrder(companyName, price, tradingPartyName, type);
            if (currentCompany.updateBuyPrice(price)) {
                isQuoteUpdated = true;
            }
            currentCompany.addBuyOrder(newBuyOrder);
            currentCompany.sortBuyOrderList();
        } else {
            SellOrder newSellOrder = new SellOrder(companyName, price, tradingPartyName, type);
            if (currentCompany.updateSellPrice(price)) {
                isQuoteUpdated = true;
            }
            currentCompany.addSellOrder(newSellOrder);
            currentCompany.sortSellOrderList();
        }

        if (isQuoteUpdated) {
            exchange.writeToFile(currentCompany);
        }
    }

}
