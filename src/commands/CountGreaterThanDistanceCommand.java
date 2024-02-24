package commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, реализующий команду count_greater_than_distance, которая выводит количество элементов со значением поля distance больше заданного.
 */
public class CountGreaterThanDistanceCommand extends BaseCommand {

    public CountGreaterThanDistanceCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды count_greater_than_distance
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        if (manager.getIsEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        try {
            InputValidator.checkIfOneArgument(commandParts);
            double distance = Double.parseDouble(commandParts[1]);
            long count = manager.countGreaterThanDistance(distance);
            System.out.printf("Количество элементов, значение поля distance которых больше %s - %s%n", distance, count);
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("distance должен быть положительным вещественным числом");
        }

    }
}
