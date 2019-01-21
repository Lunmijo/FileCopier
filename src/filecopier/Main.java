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

        countDownLatch.await();

        System.out.println("Return flow control to the main thread");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(((FirstThread) firstThread).getPart());
        outputStream.write(((SecondThread) secondThread).getPart());

        FileOutputStream fileOutput = new FileOutputStream("result_file.pdf");
        fileOutput.write(outputStream.toByteArray());

        file.close();
        fileOutput.close();

        // if you want to wait a little Thread.sleep(2000);

        System.exit(0);
    }

    public static void setCountDownLatch() {
        Main.countDownLatch.countDown();
    }
}
