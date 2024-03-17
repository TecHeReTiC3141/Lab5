package server;

import common.routeClasses.Route;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.serverModules.AcceptConnectionModule;
import server.serverModules.CommandExecutionModule;
import server.serverModules.RequestReadModule;
import server.serverModules.SendResponseModule;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    private final int saveInterval = 300;

    /**
     * Обработчик команд
     */
    private final CommandExecutionModule executor;

    /**
     * Менеджер коллекции
     */
    private final CollectionManager manager;

    private final AcceptConnectionModule acceptConnectionModule;

    private final RequestReadModule requestReadModule;

    private final SendResponseModule sendResponseModule;

    private final int port = 1234;


    /**
     * Конструктор приложения
     */
    public Server() {
        this.manager = new CollectionManager(new Stack<Route>());
        this.executor = new CommandExecutionModule(manager);
        this.acceptConnectionModule = new AcceptConnectionModule();
        this.requestReadModule = new RequestReadModule(executor);
        this.sendResponseModule = new SendResponseModule();
    }

    // TODO: separate server into four sections: connectionHandler, requestHandler, responseHandler, and commandExecutor
    public void run() throws IOException, ClassNotFoundException {
        this.manager.loadInitialCollection();
        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)

        ServerSocketChannel channel = ServerSocketChannel.open(); // канал для сервера, который слушает порты и создает сокеты для клиентов
        channel.bind(address); // теперь канал слушает по определенному сокету
        channel.configureBlocking(false); // неблокирующий режим

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        Map<SocketChannel, StringBuilder> clientDataMap = new HashMap<>();
        startSavingTask(saveInterval);

        try {
            while (true) {
                selector.select(); // количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
                Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей

                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    iter.remove();
                    try {

                        if (key.isAcceptable()) {
                            acceptConnectionModule.handleAccept(key);
                        } else if (key.isReadable()) {
                            System.out.println("Reading...");
                            requestReadModule.handleRead(key);
                        } else if (key.isWritable()) {
                            System.out.println("Writing...");
                            sendResponseModule.handleWrite(key);
                        }
                    } catch (IOException e) {
                        System.out.println("Client disconnected");
                        key.cancel();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client is disconnected");

        }
        saveCollection();
    }

    private void saveCollection() {
        try {
            if (manager.getIsEmpty()) {
                System.out.println("Коллекция пуста, нечего сохранять.");
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = builder.newDocument();
            Element root = document.createElement("Routes");
            document.appendChild(root);
            manager.addCollectionToRoot(document, root);

        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка при сохранение");
        }
    }

    public void startSavingTask(int intervalInSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable saveTask = this::saveCollection;

        // Schedule the save task to run every n seconds
        scheduler.scheduleAtFixedRate(saveTask, 0, intervalInSeconds, TimeUnit.SECONDS);
    }
}

