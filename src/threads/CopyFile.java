package threads;

import filecopier.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * In this class file copies into the other directory
 */

public class CopyFile implements Runnable {

    private File fileToCopy;
    private String resultPathWithoutFileName;

    public CopyFile(File fileToCopy, String resultPathWithoutFileName) {
        this.fileToCopy = fileToCopy;
        this.resultPathWithoutFileName = resultPathWithoutFileName;
    }

    public void run() {
        RandomAccessFile tempFileToCopy = null;
        RandomAccessFile copiedFile = null;
        try {
            tempFileToCopy = new RandomAccessFile(this.fileToCopy, "r");
            copiedFile = new RandomAccessFile(resultPathWithoutFileName + "\\" + fileToCopy.getName(), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert tempFileToCopy != null;
        assert copiedFile != null;
        try {
            for (int i = 0; i < tempFileToCopy.length();) {
                tempFileToCopy.seek(i);
                byte[] temp = new byte[1024];
                tempFileToCopy.read(temp, 0, temp.length);
                copiedFile.seek(i);
                copiedFile.write(temp);
                i += temp.length;
                System.out.println("Copied " + i + "/" + tempFileToCopy.length() + " bytes of " + fileToCopy.getName());
            }
            Main.latch.countDown();
        } catch (IOException e) {
            System.out.println("Input/output exception!");
        }
        try {

            tempFileToCopy.close();
            copiedFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}