package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, предоставляющий метод для добавления элемента в коллекцию.
 * Элемент может быть как добавлен пользователем вручную, так и считан из файла или из строки скрипта.
 */
public class AddCommand extends ReadRoute {


    public AddCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager);
    }

    /**
     * Метод, считывающий route и его в коллекци.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */

    public void execute(String[] commandParts, boolean parse) {
        try {
            if (parse) {
                InputValidator.checkIfOneArgument(commandParts);
            } else {
                InputValidator.checkIfNoArguments(commandParts);
            }
            Route route = parse ? parseRoute(commandParts[1]) : readRoute();
            manager.putToCollection(route, false);
        } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException |
                 AbsentRequiredParametersException e) {
            System.err.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Должно быть ровно 2 начальных параметра (name и distance)");
        }

    }

}
