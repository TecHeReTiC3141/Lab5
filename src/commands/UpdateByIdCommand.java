package commands;

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
    public void execute(String[] commandParts, boolean parse) {
        try {
            InputValidator.checkIfOneArgument(commandParts);
            long id = Long.parseLong(commandParts[1]);
            boolean isFound = manager.findElementById(id);
            if (!isFound) {
                System.out.println("Элемент с id " + id + " не найден. Обновление не выполнено.");
                return;
            }
            Route newRoute = parse ? parseRoute(commandParts[2]) : readRoute();
            newRoute.setId(id);
            manager.updateElementById(id, newRoute);
        } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException |
                 AbsentRequiredParametersException | ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID должен быть целым числом");

        }

    }
}
