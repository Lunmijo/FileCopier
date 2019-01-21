package filecopier;

import threads.FirstThread;
import threads.SecondThread;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

     private static volatile CountDownLatch countDownLatch;
     private static FirstThread firstThread;
     private static SecondThread secondThread;

    public synchronized static void main(String[] args) throws IOException {

        countDownLatch = new CountDownLatch(2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name with extension");
        String fileName = scanner.nextLine();

         RandomAccessFile file = null;
        long sizeOfFileParts = 0;
        long fileSize = 0;

        try {
            file = new RandomAccessFile(fileName, "r");
            fileSize = file.getChannel().size();
            sizeOfFileParts = file.getChannel().size() / 2;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("An error has been occured. Cannot work with file.");
            System.exit(-1);
        }

            ExecutorService pool = Executors.newFixedThreadPool(2);

            firstThread = new FirstThread(file, (int) sizeOfFileParts);
            secondThread = new SecondThread(file, (int) fileSize);

            pool.execute(firstThread);
            pool.execute(secondThread);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
                System.out.println("Unexpected error.");
                System.exit(-1);
            }


        RandomAccessFile resultFile = new RandomAccessFile("copy_" + fileName, "rw");

        System.out.println("First threads results writing...");
        resultFile.write(firstThread.getPart1thread(), 0, firstThread.getPart1thread().length);

        resultFile.seek(resultFile.length() - 0);

        System.out.println("Second threads results writing..");
        resultFile.write(secondThread.getPart2thread(), 0, secondThread.getPart2thread().length);

        resultFile.close();

            System.out.println("Return flow control to the main thread");

            file.close();
        System.exit(0);
    }

    public static void setCountDownLatch() {
        Main.countDownLatch.countDown();
    }

    public static FirstThread getFirstThread() {
        return firstThread;
    }

    public static SecondThread getSecondThread() {
        return secondThread;
    }
}
