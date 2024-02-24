package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, объекты которого обновляют элемент коллекции по его id, заменяя его другим элементом.
 */

public class UpdateByIdCommand extends ReadRoute {

    public UpdateByIdCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection);
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
            boolean isFound = false;
            for (Route route : collection) {
                isFound |= route.getId() == id;
            }
            if (!isFound) {
                System.out.println("Элемент с id " + id + " не найден. Обновление не выполнено.");
                return;
            }
            Route newRoute = parse ? parseRoute(commandParts[2]) : readRoute();
            newRoute.setId(id);
            for (Route route : collection) {
                if (route.getId() == id) {
                    collection.remove(route);
                    collection.add(newRoute);
                    System.out.println("Элемент с id " + id + " успешно обновлен.");
                    break;
                }
            }
        } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException |
                 AbsentRequiredParametersException | ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID должен быть целым числом");
        }

    }
}
