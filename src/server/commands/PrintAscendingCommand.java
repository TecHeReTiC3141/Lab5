package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду print_ascending, которая выводит элементы коллекции, отсортированные в естественном порядке.
 */
public class PrintAscendingCommand extends BaseCommand {

    public PrintAscendingCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды print_ascending.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public String execute(String[] commandParts) {

        return manager.printAscendingCommand();
    }
}
