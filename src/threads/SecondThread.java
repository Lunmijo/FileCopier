package threads;

import filecopier.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SecondThread implements Runnable {

    private byte[] part2thread;
    private RandomAccessFile file;
    private long tempBytesSize;

    public SecondThread(RandomAccessFile file, int arrayFullSize) {

        this.file = file;

        tempBytesSize = arrayFullSize % 2 == 0 ? arrayFullSize/2 : arrayFullSize/2 + 1;

        System.out.println(arrayFullSize % 2 == 0);

        this.part2thread = new byte[(int)tempBytesSize];

    }

    public void run() {
        try {
            if (Main.getFirstThread().getState()) {

                System.out.println("Start the second thread.");

                try {
                    file.read(part2thread, 0, (int) tempBytesSize);
                } catch (IOException e) {
                    System.out.println("Input/output exception");
                }

                System.out.println("I am the second thread and I read second part2thread of the file you gave me.");

                Main.setCountDownLatch();

            }
            else {
                this.run();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        }

    public byte[] getPart2thread() {
        return part2thread;
    }
}
