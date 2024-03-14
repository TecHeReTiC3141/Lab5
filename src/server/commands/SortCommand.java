package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду sort, которая сортирует коллекцию по возрастанию в естественном порядке.
 */

public class SortCommand extends BaseCommand {

    public SortCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды sort.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */
    public void execute(String[] commandParts) {
        manager.sortCollection();
        System.out.println("Коллекция успешно отсортирована");
    }
}
