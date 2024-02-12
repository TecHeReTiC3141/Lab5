package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду count_greater_than_distance, которая выводит количество элементов со значением поля distance больше заданного.
 */
public class CountGreaterThanDistanceCommand {

    /**
     * Метод, реализующий логику команды count_greater_than_distance
     *
     * @param collection коллекция, в которой производится поиск
     * @param distance   значение поля distance, с которым производится сравнение
     */

    public void mainMethod(Stack<Route> collection, double distance) {
        long count = collection.stream().filter(route -> route.getDistance() > distance).count();
        System.out.printf("Количество элементов, значение поля distance которых больше %s - %s%n", distance, count);
    }
}
