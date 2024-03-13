package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(1000);
        Path inFile = Paths.get("src", "tests", "in.txt");
        Path outFile = Paths.get("src", "tests", "out.txt");
        int iterations = 1;
        try (FileChannel input = FileChannel.open(inFile); RandomAccessFile write = new RandomAccessFile(outFile.toFile(), "rw");
             FileChannel output = write.getChannel()) {
            buffer.clear();
            int nBytes = input.read(buffer);
            System.out.println(nBytes);
            buffer.flip();
            nBytes = output.write(buffer);

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

//            while (nBytes != -1) {
//                buffer.flip();
//                while (buffer.hasRemaining()) {
//                    System.out.print((char) buffer.get());
//                }
//                buffer.clear();
//                nBytes = input.read(buffer);
//                ++iterations;
//            }
        System.out.println("Iterations: " + iterations);
    }
}
