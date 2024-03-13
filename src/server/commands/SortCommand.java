package server.commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.InputValidator;

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
        try {
            InputValidator.checkIfNoArguments(commandParts);
            manager.sortCollection();
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }
    }
}
