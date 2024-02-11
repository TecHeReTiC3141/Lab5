package commands;

import routeClasses.Route;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс, реализующий команду sort, которая сортирует коллекцию по возрастанию в естественном порядке.
 */

public class SortCommand {

    /**
     * Метод, реализующий логику команды sort.
     *
     * @param collection коллекция, которую нужно отсортировать
     */
    public void mainMethod(Stack<Route> collection) {
//        collection.sort(Route::compareTo);
        ArrayList<Route> routes = new ArrayList<>(collection);
        routes.sort(Route::compareTo);
        collection.clear();
        for (Route route : routes) {
            collection.push(route);
        }
        System.out.println("Коллекция успешно отсортирована");
    }
}
