package server.commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, реализующий команду info, выводящую информацию о коллекции (тип, дата инициализации, количество элементов).
 */

public class InfoCommand extends BaseCommand {


    public InfoCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды info
     *
     * @param commandParts название и аргументы команды
     */
    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            System.out.println("Тип коллекции: " + manager.getCollectionClassName());
            System.out.println("Дата инициализации: " + manager.getInitDate());
            System.out.println("Количество элементов: " + manager.getCollectionSize());
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
