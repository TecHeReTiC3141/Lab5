package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, реализующий команду show, которая выводит содержимое коллекции.
 */

public class ShowCommand extends BaseCommand {

    public ShowCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команду show.
     *
     * @param commandParts название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            if (collection.isEmpty()) {
                System.out.println("Коллекция пуста");
                return;
            }
            System.out.println("Содержимое коллекции:");
            for (Route route : collection) {
                System.out.println(route);
            }
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
