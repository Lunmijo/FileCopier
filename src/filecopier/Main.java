package filecopier;

import threads.CopyFile;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static CountDownLatch latch;

    public static void main(String[] args) {
        System.out.println("Enter absolute path to folder to copy: ");
        Scanner scanner = new Scanner(System.in);
        String copyFrom = scanner.nextLine();

        System.out.println("Enter absolute path to result folder: ");
        String copyTo = scanner.nextLine();

        File copyFromFolder = new File(copyFrom);

        if (copyFromFolder.exists() && copyFromFolder.isDirectory()) {

            LinkedList<File> fileList = new LinkedList<>();

            for (File file : Objects.requireNonNull(copyFromFolder.listFiles())) {
                if(!file.isDirectory()) {
                    fileList.add(file);
                }
            }

            latch = new CountDownLatch(fileList.size());

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            File folder = new File(copyTo);
            folder.mkdir();

            for (File file : fileList) {
               executorService.execute(new CopyFile(file, copyTo));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("End.");
            System.exit(0);


        } else {
            System.out.println("File does not exist!");
        }
    }
}