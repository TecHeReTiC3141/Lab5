package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду show, которая выводит содержимое коллекции.
 */

public class ShowCommand {

    /**
     * Метод, реализующий логику команду show.
     *
     * @param collection коллекция, содержимое которой нужно вывести
     */

    public void mainMethod(Stack<Route> collection) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        System.out.println("Содержимое коллекции:");
        for (Route route : collection) {
            System.out.println(route);
        }
    }
}
