package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду show, которая выводит содержимое коллекции.
 */

public class ShowCommand extends BaseCommand {

    public ShowCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
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
