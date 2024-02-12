package commands;

import routeClasses.Route;
import routeClasses.RouteDistanceComparator;

import java.util.Stack;

/**
 * Класс, реализующий команду print_field_descending_distance,
 * которая выводит значения поля distance элементов коллекции в порядке убывания.
 */

public class PrintFieldDescendingDistanceCommand {

    /**
     * Метод, реализующий логику  команды print_field_descending_distance.
     *
     * @param collection коллекция, над которой выполняется команда
     */

    public void mainMethod(Stack<Route> collection) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        System.out.println("Поля distance в порядке убывания:");
        collection.stream().sorted(new RouteDistanceComparator()).forEach(r -> System.out.printf("%s - %s%n", r.getId(), r.getDistance()));
    }
}
