package tests.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 1234;

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(port));
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                try (SocketChannel socket = server.accept()) {
                    System.out.println("Client connected: " + socket.getRemoteAddress());
                    // Receive data from tests.client
                    ArrayList<String> receivedData = new ArrayList<>();

                    ByteBuffer lengthBuffer = ByteBuffer.allocate(Integer.BYTES);
                    int bytesRead = socket.read(lengthBuffer);

                    while (bytesRead != -1) {
                        lengthBuffer.flip();
                        int length = lengthBuffer.getInt();
                        if (length == 0) {
                            break;
                        }
                        lengthBuffer.clear();
                        System.out.println(length);
                        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
                        socket.read(dataBuffer);
                        dataBuffer.flip();

                        byte[] bytes = new byte[length];
                        dataBuffer.get(bytes);
                        receivedData.add(new String(bytes, StandardCharsets.UTF_8));
                        System.out.println(receivedData.get(receivedData.size() - 1));

                        lengthBuffer.clear();
                        bytesRead = socket.read(lengthBuffer);
                        System.out.println(bytesRead);
                    }
                    for (String str : receivedData) {
                        String modifiedString = str + " processed";
                        System.out.println("sending " + modifiedString);
                        byte[] strBytes = modifiedString.getBytes(StandardCharsets.UTF_8);
                        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + strBytes.length);
                        buffer.putInt(strBytes.length);
                        buffer.put(strBytes);
                        buffer.flip();

                        socket.write(buffer);
                    }

                    System.out.println("Data sent back to tests.client");
                } catch (IOException e) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting tests.server: " + e.getMessage());
        }

    }

    private static ArrayList<String> getStrings(byte[] receivedData) {
        String receivedString = new String(receivedData, StandardCharsets.UTF_8);
        String[] receivedStrings = receivedString.split("\n");
        ArrayList<String> data = new ArrayList<>();
        for (String str : receivedStrings) {
            data.add(str);
        }

        // Process received data
        ArrayList<String> processedData = new ArrayList<>();
        for (String str : data) {
            processedData.add(str + " proceed");
        }
        return processedData;
    }
}
