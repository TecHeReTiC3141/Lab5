package tests.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        String serverAddress = "localhost";
        int serverPort = 1234;

        try (SocketChannel socket = SocketChannel.open(new InetSocketAddress(serverAddress, serverPort))) {
            System.out.println("Connected to tests.server");

            ArrayList<String> dataToSend = new ArrayList<>();
            for (int i = 0; i < 100; ++i) {
                dataToSend.add("String " + i);
            }
            for (String str : dataToSend) {
                byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
                ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + strBytes.length); // Size of int + size of string
                buffer.putInt(strBytes.length);
                buffer.put(strBytes);
                buffer.flip();
                System.out.println("Sending " + str);
                socket.write(buffer);

            }

            ByteBuffer endMarker = ByteBuffer.allocate(Integer.BYTES);
            endMarker.putInt(0); // Sending 0 as the length to indicate end of data
            endMarker.flip();
            socket.write(endMarker);
            ByteBuffer lengthBuffer = ByteBuffer.allocate(Integer.BYTES);
            int bytesRead = socket.read(lengthBuffer);
            while (bytesRead != -1) {
                lengthBuffer.flip();
                int length = lengthBuffer.getInt();
                lengthBuffer.clear();

                ByteBuffer receiveBuffer = ByteBuffer.allocate(length);
                socket.read(receiveBuffer);
                receiveBuffer.flip();

                byte[] bytes = new byte[length];
                receiveBuffer.get(bytes);
                System.out.println("Got " + new String(bytes, StandardCharsets.UTF_8));

                bytesRead = socket.read(lengthBuffer);
            }
            System.out.println("Finished");


            // Send data to tests.server
//            ByteBuffer sendBuffer = ByteBuffer.wrap(sendData);
//            socket.write(sendBuffer);
//            sendBuffer.clear();
//
//            // Receive response from tests.server
//            ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
//            int bytesRead = socket.read(receiveBuffer);
//            receiveBuffer.flip(); // Prepare buffer for reading
//            if (bytesRead == -1) {
//                System.out.println("No response from tests.server");
//                return;
//            }
//            while (bytesRead != -1) {
//                byte[] receivedData = new byte[bytesRead];
//                receiveBuffer.get(receivedData);
//
//                // Print received strings with "proceed" added
//                String receivedString = new String(receivedData, StandardCharsets.UTF_8);
//                System.out.println("Received from tests.server: " + receivedString);
//
//                receiveBuffer.clear();
//                bytesRead = socket.read(receiveBuffer);
//            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
