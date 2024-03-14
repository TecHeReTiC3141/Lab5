package server.commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, объекты которого обновляют элемент коллекции по его id, заменяя его другим элементом.
 */

public class UpdateByIdCommand extends ReadRoute {

    public UpdateByIdCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, реализующий логику команды update.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */
    public String execute(String[] commandParts, boolean parse) {
        try {
            if (parse) {
                InputValidator.checkIfTwoArguments(commandParts);
            } else {
                InputValidator.checkIfOneArgument(commandParts);
            }
            long id = Long.parseLong(commandParts[1]);
            boolean isFound = manager.findElementById(id);
            if (!isFound) {
                return "Элемент с id " + id + " не найден. Обновление не выполнено.";
            }
            Route newRoute = parse ? parseRoute(commandParts[2]) : readRoute();
            newRoute.setId(id);
            return manager.updateElementById(id, newRoute);
        } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException |
                 AbsentRequiredParametersException | ArrayIndexOutOfBoundsException e) {
            return e.getMessage();
        }
    }
}
