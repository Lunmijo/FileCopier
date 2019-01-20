package threads;

import filecopier.Main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FirstThread implements Runnable {
    private byte[] part;
    RandomAccessFile file;

    public FirstThread(RandomAccessFile file, int byteArraySize) {
        this.file = file;
        this.part = new byte[byteArraySize];
    }

    public void run() {
        try {
            System.out.println("1");
            file.read(part, 0, part.length);
            System.out.println("I am the first thread and I read second part of the file you gave me.");
            Main.setCountDownLatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPart() {
        return part;
    }
}
