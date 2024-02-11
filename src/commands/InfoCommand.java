package commands;

import routeClasses.Route;

import java.util.Date;
import java.util.Stack;

/**
 * Класс, реализующий команду info, выводящую информацию о коллекции (тип, дата инициализации, количество элементов).
 */

public class InfoCommand {

    /**
     * Метод, реализующий логику команды info
     *
     * @param collection коллекция, информацию о которой нужно вывести
     * @param initDate   дата инициализации коллекции (берется из console)
     */
    public void mainMethod(Stack<Route> collection, Date initDate) {
        System.out.printf("Тип коллекции: %s%n", collection.getClass().getName());
        System.out.printf("Дата инициализации: %s%n", initDate);
        System.out.printf("Количество элементов: %s%n", collection.size());
    }
}
