import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MergeSort {
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
            mergeSort((ArrayList) AverageRating, (ArrayList) User);
            convertToCSV((ArrayList) AverageRating, (ArrayList) User);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter rank of user you want average rating for: ");
            Integer ranking = scanner.nextInt() + 1;
            get(ranking);
        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    private static void mergeSort(ArrayList inputArray, ArrayList userArray) {
        int inputLength = inputArray.size();

        if (inputLength < 2) {
            return;
        }

        int midIndex = inputLength / 2;
        List leftHalf = new ArrayList(inputArray.subList(0, midIndex));
        List rightHalf = new ArrayList(inputArray.subList(midIndex, inputLength));

        List leftHalfuser = new ArrayList(userArray.subList(0, midIndex));
        List rightHalfuser = new ArrayList(userArray.subList(midIndex, inputLength));

        mergeSort((ArrayList) leftHalf, (ArrayList) leftHalfuser);
        mergeSort((ArrayList) rightHalf, (ArrayList) rightHalfuser);

        merge(inputArray, (ArrayList) leftHalf, (ArrayList) rightHalf, userArray, (ArrayList) leftHalfuser, (ArrayList) rightHalfuser);
    }

    private static void merge(ArrayList inputArray, ArrayList leftHalf, ArrayList rightHalf, ArrayList userArray, ArrayList leftHalfuser, ArrayList rightHalfuser) {
        int leftSize = leftHalf.size();
        int rightSize = rightHalf.size();
        int i = 0, j = 0, k = 0;

        while (i < leftSize && j < rightSize) {
            if ((double) leftHalf.get(i) >= (double) rightHalf.get(j)) {
                inputArray.set(k, leftHalf.get(i));
                userArray.set(k, leftHalfuser.get(i));
                i++;
            } else {
                inputArray.set(k, rightHalf.get(j));
                userArray.set(k, rightHalfuser.get(j));
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            inputArray.set(k, leftHalf.get(i));
            userArray.set(k, leftHalfuser.get(i));
            i++;
            k++;
        }

        while (j < rightSize) {
            inputArray.set(k, rightHalf.get(j));
            userArray.set(k, rightHalfuser.get(j));
            j++;
            k++;
        }
    }

    private static void convertToCSV(ArrayList inputArray, ArrayList userArray) throws FileNotFoundException {
        File csvFile = new File("Rating-Results.csv");
        PrintWriter out = new PrintWriter(csvFile);

        for (int i = 0; i < inputArray.size(); i++) {
            out.printf("%d,%,.2f\n", userArray.get(i), inputArray.get(i));
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

            System.out.printf("User ranked %d: %d, Average: %,.2f", userRanking - 1, User.get(userRanking-2), AverageRating.get(userRanking-2));

        } catch(IOException e){
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
}

