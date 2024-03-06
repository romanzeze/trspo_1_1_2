import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int[] finalMerge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i=0;
        int j=0;
        int r=0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[r]=a[i];
                i++;
                r++;
            } else {
                result[r]=b[j];
                j++;
                r++;
            }
            if (i==a.length) {
                while (j<b.length) {
                    result[r]=b[j];
                    r++;
                    j++;
                }
            }
            if (j==b.length) {
                while (i<a.length) {
                    result[r]=a[i];
                    r++;
                    i++;
                }
            }
        }

        return result;
    }

    static void printArray(int[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    static void writeToFile(int[] arr, String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            for (int i = 0; i < arr.length; i++) {
                fw.write(arr[i] + " ");
            }
            System.out.println("Successfully written");
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static int[] readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner s = new Scanner(file);
            int size = 0;
            while (s.hasNextInt()) {
                size++;
                s.nextInt();
            }

            int[] arr = new int[size];
            Scanner s1 = new Scanner(file);
            for (int i = 0; i < size; i++)
                arr[i] = s1.nextInt();
            return arr;
        } catch (Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public static int[] generateInput() {
        int size;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.print("Size of the array: ");
        size = scan.nextInt();

        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }
    public static void main(String[] args) throws InterruptedException {
        int[] result;
        Scanner scan = new Scanner(System.in);
        int in;
        do {
            System.out.println("Choose input method: 1 - generate randomly; 2 - input from file");
            in = scan.nextInt();
        } while (in != 1 && in != 2);

        int[] original;
        if (in == 1) {
            original = generateInput();
        } else {
            original = readFromFile("input.txt");
        }

        do {
            System.out.println("Display output - 1; write to file - 2; only calculate time - 3");
            in = scan.nextInt();
        } while (in != 1 && in != 2 && in != 3);

        if (in == 1) {
            System.out.println("Given Array");
            printArray(original);

            long startTime = System.currentTimeMillis();
            int[] subArr1 = new int[original.length/2];
            int[] subArr2 = new int[original.length - original.length/2];
            System.arraycopy(original, 0, subArr1, 0, original.length/2);
            System.arraycopy(original, original.length/2, subArr2, 0, original.length - original.length/2);

            Worker runner1 = new Worker(subArr1);
            Worker runner2 = new Worker(subArr2);
            runner1.start();
            runner2.start();
            runner1.join();
            runner2.join();
            result = finalMerge (runner1.getInternal(), runner2.getInternal());
            long stopTime = System.currentTimeMillis();

            System.out.println("\nSorted array");
            printArray(result);
            System.out.println("\nThat took " + (stopTime - startTime) + " milliseconds");
        } else if (in == 2) {
            long startTime = System.currentTimeMillis();
            int[] subArr1 = new int[original.length/2];
            int[] subArr2 = new int[original.length - original.length/2];
            System.arraycopy(original, 0, subArr1, 0, original.length/2);
            System.arraycopy(original, original.length/2, subArr2, 0, original.length - original.length/2);

            Worker runner1 = new Worker(subArr1);
            Worker runner2 = new Worker(subArr2);
            runner1.start();
            runner2.start();
            runner1.join();
            runner2.join();
            result = finalMerge (runner1.getInternal(), runner2.getInternal());
            long stopTime = System.currentTimeMillis();

            writeToFile(result, "output.txt");
            System.out.println("\nThat took " + (stopTime - startTime) + " milliseconds");
        } else {
            long startTime = System.currentTimeMillis();
            int[] subArr1 = new int[original.length/2];
            int[] subArr2 = new int[original.length - original.length/2];
            System.arraycopy(original, 0, subArr1, 0, original.length/2);
            System.arraycopy(original, original.length/2, subArr2, 0, original.length - original.length/2);

            Worker runner1 = new Worker(subArr1);
            Worker runner2 = new Worker(subArr2);
            runner1.start();
            runner2.start();
            runner1.join();
            runner2.join();
            result = finalMerge (runner1.getInternal(), runner2.getInternal());
            long stopTime = System.currentTimeMillis();

            System.out.println("\nThat took " + (stopTime - startTime) + " milliseconds");
        }
    }
}