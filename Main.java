import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;
import static java.lang.Double.*;
import static java.lang.System.out;

public class Main {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        List Item = new ArrayList();
        List Quantity = new ArrayList();
        List SoldPrice = new ArrayList();
        List AddPrice = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        out.println("Input inventory below: \n");
        double profit = 0;

        while (true) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String command = parts[0];

            if (command.equals("CHECK")) {
                for (int i = 0; i < Item.size(); i++) {
                    String item = (String) Item.get(i);
                    Integer quantity = (Integer) Quantity.get(i);
                    if (quantity == 1) {
                        out.println("\nThere is " + quantity + " " + item);
                    } else {
                        out.println("\nThere are " + quantity + " " + item + "s");
                    }
                }
            } else if (command.equals("PROFIT")) {
                if (profit > 0) {
                    out.println("\nProfit: $" + df.format(profit));
                    break;
                } else {
                    out.println("\nLoss: $" + (df.format((-1 * profit))));
                    break;
                }
            } else if (command.equals("ADD")) {
                String itemname = parts[1];
                Integer itemquantity = Integer.valueOf(parts[2]);
                Double itemprice = valueOf(parts[3]);
                if (!Item.contains(itemname)) {
                    Item.add(itemname);
                    Quantity.add(itemquantity);
                    SoldPrice.add(0);
                    AddPrice.add(itemprice);
                } else {
                    int temp = Item.indexOf(itemname);
                    int newquantity = (int) Quantity.get(temp) + itemquantity;
                    Quantity.set(temp, newquantity);
                    AddPrice.set(temp, itemprice);
                }
            } else if (command.equals("SELL")) {
                String itemname = parts[1];
                Integer itemquantity = Integer.valueOf(parts[2]);
                Double itemprice = valueOf(parts[3]);
                int temp = Item.indexOf(itemname);
                int olditemquantity = Quantity.indexOf(temp);
                if (itemprice > 0 && itemquantity < olditemquantity) {
                    double difference = itemprice - (double) AddPrice.get(temp);
                    profit += itemquantity * difference;
                    int newquantity = (int) Quantity.get(temp) - itemquantity;
                    Quantity.set(temp, newquantity);
                    SoldPrice.set(temp, itemprice);
                } else {
                    out.println("\nProfit/Loss: NA");
                    break;
                }
            } else if (command.equals("RETURN")) {
                String itemname = parts[1];
                Integer itemquantity = Integer.valueOf(parts[2]);
                Double itemprice = valueOf(parts[3]);
                boolean itemexists = false;

                for (int i = 0; i < Item.size(); i++) {
                    if (itemname == Item.get(i)) {
                        itemexists = true;
                    }
                }
                if (itemexists == false) {
                    out.println("\nProfit/Loss: NA");
                    break;
                }

                int temp = Item.indexOf(itemname);
                double difference = itemprice - (double) AddPrice.get(temp);
                profit -= itemquantity * difference;
            } else if (command.equals("WRITEOFF")) {
                String itemname = parts[1];
                Integer itemquantity = Integer.valueOf(parts[2]);
                int temp = Item.indexOf(itemname);
                profit -= itemquantity * (double) AddPrice.get(temp);
                if (((int) Quantity.get(temp) - itemquantity) > 0) {
                    int newquantity = (int) Quantity.get(temp) - itemquantity;
                    Quantity.set(temp, newquantity);
                } else {
                    out.println("\nProfit/Loss: NA");
                    break;
                }
            } else if (command.equals("DONATE")) {
                String itemname = parts[1];
                Integer itemquantity = Integer.valueOf(parts[2]);
                int temp = Item.indexOf(itemname);
                if (((int) Quantity.get(temp) - itemquantity) > 0) {
                    int newquantity = (int) Quantity.get(temp) - itemquantity;
                    Quantity.set(temp, newquantity);
                } else {
                    out.println("\nProfit/Loss: NA");
                    break;
                }
            }
        }
    }

    public class intoarray {

        List User = new ArrayList();
        List TotalRating = new ArrayList();
        List NumberOfRatings = new ArrayList();
        List AverageRating = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input rating file path below: \n");
        while(true)
        {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            Integer source = Integer.valueOf(parts[0]);
            Integer target = Integer.valueOf(parts[1]);
            Integer rating = Integer.valueOf(parts[2]);

            boolean userexists = false;

            if (!User.contains(source)) {
                User.add(source);
                TotalRating.add(0);
                NumberOfRatings.add(0);

                if (!User.contains(target)){
                    User.add(target);
                    Integer targetindex = User.indexOf(target);
                    TotalRating.set(targetindex, rating);
                    NumberOfRatings.set(targetindex, 1);
                } else {
                    Integer targetindex = User.indexOf(target);
                    Integer toadd = (Integer) TotalRating.get(targetindex);
                    Integer numberofratings = (Integer) NumberOfRatings.get(targetindex);

                    toadd += rating;
                    TotalRating.set(targetindex, toadd);

                    numberofratings += 1;
                    NumberOfRatings.set(targetindex, numberofratings);
                }
            } else {
                if (!User.contains(target)){
                    User.add(target);
                    Integer targetindex = User.indexOf(target);
                    TotalRating.set(targetindex, rating);
                    NumberOfRatings.set(targetindex, 1);
                } else {
                    Integer targetindex = User.indexOf(target);
                    Integer toadd = (Integer) TotalRating.get(targetindex);
                    Integer numberofratings = (Integer) NumberOfRatings.get(targetindex);

                    toadd += rating;
                    TotalRating.set(targetindex, toadd);

                    numberofratings += 1;
                    NumberOfRatings.set(targetindex, numberofratings);
                }
            }
            for (int i = 0; i < User.size(); i++) {
                Integer totalrating = (Integer) TotalRating.get(i);
                Integer numberofratings = (Integer) NumberOfRatings.get(i);

                Integer averagerating = totalrating / numberofratings;

                AverageRating.set(i, averagerating);
                
            }
        }
    }
}