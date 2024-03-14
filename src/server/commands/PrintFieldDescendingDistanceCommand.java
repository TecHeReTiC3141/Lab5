package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду print_field_descending_distance,
 * которая выводит значения поля distance элементов коллекции в порядке убывания.
 */

public class PrintFieldDescendingDistanceCommand extends BaseCommand {

    public PrintFieldDescendingDistanceCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику  команды print_field_descending_distance.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public String execute(String[] commandParts) {

        return manager.printDescendingDistance();
    }
}
