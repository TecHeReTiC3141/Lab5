package client;

import client.validators.*;
import exceptions.ExitException;
import exceptions.UnknownCommandException;
import utils.SystemInConsole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {

    private final Map<String, BaseValidator> validators;

    public Client() {
        validators = new HashMap<>() {
            {
                put("help", new NoArgumentsValidator());
                put("info", new NoArgumentsValidator());
                put("show", new NoArgumentsValidator());
                put("add", new AddValidator());
                put("update", new UpdateValidator());
                put("remove_by_id", new OneIntArgValidator());
                put("clear", new NoArgumentsValidator());
                put("remove_at", new OneIntArgValidator());
                put("reorder", new NoArgumentsValidator());
                put("save", new NoArgumentsValidator());
                put("sort", new NoArgumentsValidator());
                put("count_greater_than_distance", new OneIntArgValidator());
                put("print_ascending", new NoArgumentsValidator());
                put("print_field_descending_distance", new NoArgumentsValidator());
                put("exit", new ExitValidator());
            }
        };
    }


    private final int port = 1234;


    public void run() {
        try (SocketChannel socketChannel = SocketChannel.open();) {
            socketChannel.connect(new InetSocketAddress("localhost", port));
            System.out.println("Client Socket connected");

            SystemInConsole sc = new SystemInConsole();


            System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
            while (true) {
                String line = sc.getLine();
                if (line.isEmpty()) {
                    continue;
                }

                ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(line.trim().split(" ")));

                String commandName = commandParts.get(0).toLowerCase();
                commandParts.remove(0);

                try {
                    BaseValidator.checkIsValidCommand(commandName, validators.keySet());
                    Request request = validators.get(commandName).validate(commandName, commandParts.toArray(new String[0]));
                    if (request == null) {
                        continue;
                    }

                    System.out.println(request);
                    // TODO: do not create new stream every time
                    ByteArrayOutputStream bais = new ByteArrayOutputStream();
                    ObjectOutputStream toServer = new ObjectOutputStream(bais);
                    toServer.writeObject(request);

                    socketChannel.write(ByteBuffer.wrap(bais.toByteArray()));


                    // Receive response from the server
                    ByteBuffer fromServer = ByteBuffer.allocate(1024);
                    socketChannel.read(fromServer);
                    String response = new String(fromServer.array()).trim();


                    // Display the response received from the server
                    System.out.println("Server response: " + response);
                    System.out.println("-----------------------------------\n");
                } catch (UnknownCommandException e) {
                    System.out.println(e.getMessage());
                } catch (ExitException e) {
                    System.out.println("Выход из программы...");
                    break;
                }  catch (NoSuchElementException e) {
                    System.err.println("Достигнут конец ввода, завершение работы программы...");
                    System.exit(130);
                }
            }

            socketChannel.close();
            System.out.println("Socket channel closed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
