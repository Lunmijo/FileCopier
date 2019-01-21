package threads;

import filecopier.Main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FirstThread implements Runnable {
    private byte[] part1thread;
    private RandomAccessFile file;

    public FirstThread(RandomAccessFile file, int byteArraySize) {
        this.file = file;
        this.part1thread = new byte[byteArraySize];
    }

    public void run() {
        try {
            System.out.println("Start the first thread.");

            file.read(part1thread, 0, part1thread.length);

            System.out.println("I am the first thread and I read first part1thread of the file you gave me.");

            Main.setCountDownLatch();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPart1thread() {
        return part1thread;
    }

    public boolean getState() {
        return Thread.currentThread().isAlive();
    }
}
