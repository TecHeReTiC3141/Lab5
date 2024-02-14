package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import routeClasses.RouteDistanceComparator;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду print_field_descending_distance,
 * которая выводит значения поля distance элементов коллекции в порядке убывания.
 */

public class PrintFieldDescendingDistanceCommand extends BaseCommand {

    public PrintFieldDescendingDistanceCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, реализующий логику  команды print_field_descending_distance.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {

        try {
            InputValidator.checkIfNoArguments(commandParts);
            if (collection.isEmpty()) {
                System.out.println("Коллекция пуста.");
                return;
            }
            System.out.println("Поля distance в порядке убывания:");
            collection.stream().sorted(new RouteDistanceComparator()).forEach(r -> System.out.printf("%s - %s%n", r.getId(), r.getDistance()));
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
