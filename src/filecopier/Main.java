package filecopier;

import threads.CopyFile;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
+ на вход получить исходный и результирующий каталог
+ сделать File[] listFiles()
+ удалить оттуда папки и пересохранить это всё в линкедЛист

работа с  потоками:
создавать потоки пока их число не будет =5 или не закончатся файлы.
Копировать файлы, выводя на экран "Копируется файл N/n"
Если число потоков =5 ждать пока освободятся.
 */

public class Main {

    public synchronized static void main(String[] args) {
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

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            File folder = new File(copyTo);
            folder.mkdir();

            for (File file : fileList) {
                executorService.execute(new CopyFile(file, copyTo));
            }

                System.out.println("End.");
                System.exit(0);

        } else {
            System.out.println("File does not exist!");
        }
    }
}