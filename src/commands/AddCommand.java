package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, предоставляющий метод для добавления элемента в коллекцию.
 * Элемент может быть как добавлен пользователем вручную, так и считан из файла или из строки скрипта.
 */
public class AddCommand extends ReadRoute {


    public AddCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection);
    }

    /**
     * Метод, добавляющий route в коллекцию и устанавливающий id элемента при необходимости.
     *
     * @param collection коллекция, в которую нужно добавить элемент
     * @param route      элемент, который нужно добавить
     */

    public void putToCollection(Stack<Route> collection, Route route, boolean silence) {
        if (route.getId() == 0) {

            if (collection.isEmpty()) {
                route.setId(1);
            } else {
                long maxId = 0L;
                for (Route item : collection) {
                    maxId = Math.max(maxId, item.getId());
                }
                route.setId(maxId + 1);
            }
        }

        collection.push(route);
        if (!silence) System.out.println("Маршрут успешно добавлен в коллекцию");
    }

    /**
     * Метод, считывающий route и его в коллекци.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */

    public void execute(String[] commandParts, boolean parse) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            Route route = parse ? parseRoute(commandParts[1]) : readRoute();
            putToCollection(collection, route, false);
        } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException |
                 AbsentRequiredParametersException e) {
            System.err.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Должно быть ровно 2 начальных параметра (name и distance)");
        }

    }

}
