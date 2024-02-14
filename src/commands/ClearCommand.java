package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду clear, очищающую коллекцию.
 */

public class ClearCommand extends BaseCommand {

    public ClearCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, очищающий коллекцию.
     *
        * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            collection.clear();
            System.out.println("Коллекция очищена");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
