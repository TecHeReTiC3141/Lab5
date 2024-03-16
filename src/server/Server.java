package server;

import client.Request;
import routeClasses.Route;
import utils.CollectionManager;
import utils.CommandExecutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server {

    /**
     * Обработчик команд
     */
    private final CommandExecutor executor;

    /**
     * Менеджер коллекции
     */
    private final CollectionManager manager;
    private final int port = 1234;

    /**
     * Конструктор приложения
     */
    public Server() {
        this.manager = new CollectionManager(new Stack<Route>());
        this.executor = new CommandExecutor(manager);
    }

    public void run() throws IOException, ClassNotFoundException {

        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)

        ServerSocketChannel channel = ServerSocketChannel.open(); // канал для сервера, который слушает порты и создает сокеты для клиентов
        channel.bind(address); // теперь канал слушает по определенному сокету
        channel.configureBlocking(false); // неблокирующий режим

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        Map<SocketChannel, StringBuilder> clientDataMap = new HashMap<>();

        while (true) {
            selector.select(); // количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
            Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
            Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей

            while (iter.hasNext()) {

                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {
                    System.out.println("Reading...");
                    handleRead(key);
                } else if (key.isWritable()) {
                    System.out.println("Writing...");
                    handleWrite(key);
                }
            }
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
        SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel
        System.out.println("Connection accepted from " + client);
        client.configureBlocking(false); // неблокирующий режим
        client.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(4096));
    }

    private void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим

        ByteBuffer fromClientBuffer = (ByteBuffer) key.attachment();
        client.read(fromClientBuffer);
        try {

            ObjectInputStream fromClient = new ObjectInputStream(new ByteArrayInputStream(fromClientBuffer.array()));

            Request request = (Request) fromClient.readObject();
            fromClientBuffer.clear();
            System.out.println(request);

            byte[] response = executor.processCommand(request.getCommand(), request.getArgs(), request.getRoute()).getBytes();
            ByteBuffer responseBuffer = ByteBuffer.wrap(response);
            client.register(key.selector(), SelectionKey.OP_WRITE, responseBuffer);
        } catch (StreamCorruptedException e) {
            System.out.println("Client disconnected");
            key.cancel();
        }
    }

    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        client.write(buffer);
        client.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(4096));
    }
}
