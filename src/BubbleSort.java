import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BubbleSort {
    public static void main(String[] args) {
        List User = new ArrayList();
        List AverageRating = new ArrayList();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader("Rating-Results.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] rowdata = line.split(",");
                Integer user = Integer.valueOf(rowdata[0]);
                Double average = Double.valueOf(rowdata[1]);

                User.add(user);
                AverageRating.add(average);
            }
            bubbleSort((ArrayList) AverageRating, (ArrayList) User);
            convertToCSV((ArrayList) AverageRating, (ArrayList) User);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter rank of user you want average rating for: ");
            Integer ranking = scanner.nextInt() + 1;
            get(ranking);
        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    private static void convertToCSV(ArrayList inputArray, ArrayList userArray) throws FileNotFoundException {
        File csvFile = new File("Rating-Results.csv");
        PrintWriter out = new PrintWriter(csvFile);

        for (int i = 0; i < inputArray.size(); i++) {
            out.printf("%d,%.2f\n", userArray.get(i), inputArray.get(i));
        }

        out.close();
    }

    private static void get(int userRanking){
        List User = new ArrayList();
        List AverageRating = new ArrayList();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader("Rating-Results.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] rowdata = line.split(",");
                Integer user = Integer.valueOf(rowdata[0]);
                Double average = Double.valueOf(rowdata[1]);

                User.add(user);
                AverageRating.add(average);
            }

            System.out.printf("User ranked %d: %d, Average: %.2f", userRanking - 1, User.get(userRanking - 2), AverageRating.get(userRanking - 2));

        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    private static void bubbleSort(ArrayList arrayList, ArrayList userArray){
        boolean swapped = true;

        while (swapped){
            swapped = false;

            for (int i = 0; i < arrayList.size() - 1; i++){
                double first = (double) arrayList.get(i);
                double second = (double) arrayList.get(i+1);
                int usertemp = (int) userArray.get(i);
                if (first < second){
                    swapped = true;
                    arrayList.set(i, second);
                    arrayList.set(i+1, first);

                    userArray.set(i, userArray.get(i+1));
                    userArray.set(i+1, usertemp);
                }
            }
        }
    }
}
