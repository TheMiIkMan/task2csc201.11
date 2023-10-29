import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RatingCounter {
    public static void main(String[] args) throws FileNotFoundException {
        List User = new ArrayList();
        List TotalRating = new ArrayList();
        List NumberOfRatings = new ArrayList();
        List AverageRating = new ArrayList();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String csvFilePath = scanner.nextLine();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] rowdata = line.split(",");
                String sourcestring = rowdata[0];
                String targetstring = rowdata[1];
                String ratingstring = rowdata[2];

                Integer source = Integer.valueOf(sourcestring);
                Integer target = Integer.valueOf(targetstring);
                Double rating = Double.valueOf(ratingstring);

                if (rating > 10 || rating < -10) {
                    System.out.println("Rating outside of -10 and 10");
                    break;
                }

                if (!User.contains(source)) {
                    User.add(source);
                    TotalRating.add(0.00);
                    NumberOfRatings.add(0.00);
                    //AverageRating.add(0);
                }
                if (!User.contains(target)) {
                    User.add(target);
                    TotalRating.add(rating);
                    NumberOfRatings.add(1.00);
                } else {
                    Integer targetindex = User.indexOf(target);
                    Double toadd = (Double) TotalRating.get(targetindex);
                    Double numberofratings = (Double) NumberOfRatings.get(targetindex);

                    toadd += rating;
                    TotalRating.set(targetindex, toadd);

                    numberofratings += 1;
                    NumberOfRatings.set(targetindex, numberofratings);
                }
            }

            for (int i = 0; i < User.size(); i++) {
                Double totalrating = (Double) TotalRating.get(i);
                Double numberofratings = (Double) NumberOfRatings.get(i);
                Double averagerating = 0.00;
                if (numberofratings != 0) {
                    averagerating = totalrating / numberofratings;
                }
                AverageRating.add(averagerating);
                }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }

        convertToCSV((ArrayList) AverageRating, (ArrayList) User);

    }
    private static void convertToCSV(ArrayList inputArray, ArrayList userArray) throws FileNotFoundException{
        File csvFile = new File("Rating-Results.csv");
        PrintWriter out = new PrintWriter(csvFile);

        for (int i = 0; i < inputArray.size(); i++) {
            out.printf("%d,%,.2f\n",userArray.get(i), inputArray.get(i));
        }

        out.close();
    }
}
