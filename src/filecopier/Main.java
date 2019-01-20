package filecopier;

import threads.FirstThread;
import threads.SecondThread;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

     private static CountDownLatch countDownLatch;

    public static void main(String[] args) throws IOException, InterruptedException {

        countDownLatch = new CountDownLatch(2);

        RandomAccessFile file = new RandomAccessFile("file_to_copy.pdf", "r");
        long partByteSize = file.length() / 2;

        ExecutorService pool = Executors.newFixedThreadPool(2);


        Runnable firstThread = new FirstThread(file, (int) partByteSize);
        Runnable secondThread = new SecondThread(file, (int) partByteSize);

        pool.submit(firstThread);
        pool.submit(secondThread);

        /* byte[] firstPart = new byte[(int)partByteSize];
        byte[] secondPart = new byte[(int)partByteSize];

        file.read(firstPart, 0, (int)partByteSize);
        file.seek(partByteSize - 1);
        file.read(secondPart, 0, (int)partByteSize);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(firstPart);
        outputStream.write(secondPart);*/
        //проблема в том что должны отработать FirstThread, SecondThread, потом главный, а происходит наоборот.

        countDownLatch.await();

        System.out.println("Вернули управление главному потоку");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(((FirstThread) firstThread).getPart());
        outputStream.write(((SecondThread) secondThread).getPart());

        FileOutputStream fileOutput = new FileOutputStream("result_file.pdf");
        fileOutput.write(outputStream.toByteArray());
    }

    public static CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public static void setCountDownLatch() {
        Main.countDownLatch.countDown();
    }
}
