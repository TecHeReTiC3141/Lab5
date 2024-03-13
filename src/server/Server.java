package server;

import routeClasses.Route;
import utils.CollectionManager;
import utils.CommandExecutor;

import java.util.Stack;

public class Server {

    /**
     * Обработчик команд
     */
    private final CommandExecutor executor;

    /**
     * Менеджер коллекции
     */
    private final CollectionManager manager;

    /**
     * Конструктор приложения
     */
    public Server() {
        this.manager = new CollectionManager(new Stack<Route>());
        this.executor = new CommandExecutor(manager);
    }
}
