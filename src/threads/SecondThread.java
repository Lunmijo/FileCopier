package threads;

import filecopier.Main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SecondThread implements Runnable {

    private byte[] part;
    private RandomAccessFile file;

    public SecondThread(RandomAccessFile file, int byteArraySize) {

        this.file = file;
        this.part = new byte[byteArraySize];

    }

    public void run() {
        try {

            System.out.println("Start the second thread.");

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
