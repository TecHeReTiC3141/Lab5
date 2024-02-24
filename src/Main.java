import routeClasses.Route;
import utils.CommandExecutor;

import java.util.Stack;

/**
 * Класс, содержащий метод main, который запускает консольное приложение.
 */

public class Main {

    /**
     * Точка запуска консольного приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        // TODO: повысить модульность через введение InputManager (для ввода), CollectionManager (для манипуляций с коллекциями)

        Stack<Route> routes = new Stack<>();

        CommandExecutor executor = new CommandExecutor(commands);

        Console console = new Console(routes, executor);
        console.run();
    }
}