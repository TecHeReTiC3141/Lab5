package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду reorder, которая изменяет порядок элементов коллекции на обратный.
 */

public class ReorderCommand extends BaseCommand {


    public ReorderCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, изменяющий порядок элементов коллекции на обратный.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            Stack<Route> temp = new Stack<>();
            while (!collection.isEmpty()) {
                temp.push(collection.pop());
            }

            for (Route route : temp) {
                collection.push(route);
            }
            System.out.println("Порядок элементов коллекции изменен на обратный");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
