package client;

import utils.SystemInConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    private final int port = 1234;
    public void run() {
        try (SocketChannel socketChannel = SocketChannel.open();) {
            socketChannel.connect(new InetSocketAddress("localhost", port));
            socketChannel.configureBlocking(false);
            System.out.println("Client Socket connected");

            SystemInConsole sc = new SystemInConsole();
            System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
            while (true) {
                String line = sc.getLine();

                if (line.startsWith("exit")) {
                    break;
                }

                ByteBuffer buffer = ByteBuffer.wrap(line.getBytes());
                socketChannel.write(buffer);

                // Clear the buffer for reading the response
                buffer.clear();

                // Receive response from the server
                StringBuilder response = new StringBuilder();
                int bytesRead;
                while ((bytesRead = socketChannel.read(buffer)) != -1) {
                    buffer.flip();
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    response.append(new String(data));
                    buffer.clear();
                }

                // Display the response received from the server
                System.out.println("Server response: " + response.toString());
            }

            socketChannel.close();
            System.out.println("Socket channel closed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
