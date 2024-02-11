package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду clear, очищающую коллекцию.
 */

public class ClearCommand {

    /**
     * Метод, очищающий коллекцию.
     *
     * @param collection коллекция, которую нужно очистить
     */

    public void mainMethod(Stack<Route> collection) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}
