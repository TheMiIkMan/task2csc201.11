import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickSort {
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
            quickSort((ArrayList) AverageRating, 0, AverageRating.size() - 1, (ArrayList) User);
            convertToCSV((ArrayList) AverageRating, (ArrayList) User);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter rank of user you want average rating for: ");
            Integer ranking = scanner.nextInt() + 1;
            get(ranking);
        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    private static void quickSort(ArrayList averageRating, int lowIndex, int highIndex, ArrayList user) {

        if(lowIndex >= highIndex){
            return;
        }

        double pivot = (double) averageRating.get(highIndex);

        int leftPointer = lowIndex;
        int rightPointer = highIndex;

        while(leftPointer < rightPointer){
            while((double) averageRating.get(leftPointer) <= pivot && leftPointer < rightPointer){
                leftPointer++;
            }

            while ((double) averageRating.get(rightPointer) >= pivot && leftPointer < rightPointer){
                rightPointer--;
            }

            swap(averageRating, leftPointer, rightPointer, user);
        }

        swap(averageRating, leftPointer, highIndex, user);

        quickSort(averageRating, lowIndex, leftPointer - 1, user);
        quickSort(averageRating, leftPointer + 1, highIndex, user);
    }

    private static void swap(ArrayList array, int index1, int index2, ArrayList user){
        double temp = (double) array.get(index1);
        array.set(index1, array.get(index2));
        array.set(index2, temp);

        int temp2 = (int) user.get(index1);

        user.set(index1, user.get(index2));
        user.set(index2, temp2);
    }

    private static void convertToCSV(ArrayList inputArray, ArrayList userArray) throws FileNotFoundException {
        File csvFile = new File("Rating-Results.csv");
        PrintWriter out = new PrintWriter(csvFile);

        for (int i = 0; i < inputArray.size(); i++) {
            out.printf("%d,%.2f\n", userArray.get(~(i-inputArray.size())), inputArray.get(~(i-inputArray.size())));
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

            System.out.printf("User ranked %d: %d, Average: %,.2f", userRanking - 1, (User.get(userRanking - 2)), AverageRating.get(userRanking - 2));

        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
}
