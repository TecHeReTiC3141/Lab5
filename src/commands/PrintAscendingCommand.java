package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду print_ascending, которая выводит элементы коллекции, отсортированные в естественном порядке.
 */
public class PrintAscendingCommand {

    /**
     * Метод, реализующий логику команды print_ascending.
     *
     * @param collection коллекция, элементы которой нужно вывести
     */

    public void mainMethod(Stack<Route> collection) {
        collection.stream()
                .sorted()
                .forEach(System.out::println);
    }
}
