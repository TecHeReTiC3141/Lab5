package server.commands;

import utils.CollectionManager;

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

    public String execute(String[] commandParts) {
        if (manager.getIsEmpty()) {
            return "Коллекция пуста";
        }
        double distance = Double.parseDouble(commandParts[1]);
        long count = manager.countGreaterThanDistance(distance);
        return "Количество элементов, значение поля distance которых больше %s - %s%n".formatted(distance, count);
    }
}
