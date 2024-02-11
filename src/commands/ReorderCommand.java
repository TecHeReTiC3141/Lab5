package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду reorder, которая изменяет порядок элементов коллекции на обратный.
 */

public class ReorderCommand {

    /**
     * Метод, изменяющий порядок элементов коллекции на обратный.
     *
     * @param collection коллекция элементов класса Route
     */

    public void mainMethod(Stack<Route> collection) {
        Stack<Route> temp = new Stack<>();
        while (!collection.isEmpty()) {
            temp.push(collection.pop());
        }

        for (Route route : temp) {
            collection.push(route);
        }
        System.out.println("Порядок элементов коллекции изменен на обратный");
    }
}
