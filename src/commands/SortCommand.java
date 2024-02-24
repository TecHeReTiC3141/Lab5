package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.CollectionManager;
import utils.InputValidator;

import java.util.ArrayList;

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
            ArrayList<Route> routes = new ArrayList<>(collection);
            routes.sort(Route::compareTo);
            collection.clear();
            for (Route route : routes) {
                collection.push(route);
            }
            System.out.println("Коллекция успешно отсортирована");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }
    }
}
