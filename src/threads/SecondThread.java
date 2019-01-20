package threads;

import filecopier.Main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SecondThread implements Runnable {

    private byte[] part;
    RandomAccessFile file;

    public SecondThread(RandomAccessFile file, int byteArraySize) {
        this.file = file;
        this.part = new byte[byteArraySize];
        if (part.length == byteArraySize) {
            System.out.println("маразм");
        }
    }

    public void run() {
        try {
            System.out.println("2");
            file.seek(part.length - 1);
            file.read(part, 0, part.length);
            System.out.println("I am the second thread and I read second part of the file you gave me.");
            Main.setCountDownLatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPart() {
        return part;
    }
}
