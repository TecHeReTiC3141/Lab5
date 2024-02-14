package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду count_greater_than_distance, которая выводит количество элементов со значением поля distance больше заданного.
 */
public class CountGreaterThanDistanceCommand extends BaseCommand {

    public CountGreaterThanDistanceCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, реализующий логику команды count_greater_than_distance
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        try {
            double distance = Double.parseDouble(commandParts[1]);
            InputValidator.checkIfOneArgument(commandParts);
            long count = collection.stream().filter(route -> route.getDistance() > distance).count();
            System.out.printf("Количество элементов, значение поля distance которых больше %s - %s%n", distance, count);
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("distance должен быть положительным вещественным числом");
        }

    }
}
