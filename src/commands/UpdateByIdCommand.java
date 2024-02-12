package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, объекты которого обновляют элемент коллекции по его id, заменяя его другим элементом.
 */

public class UpdateByIdCommand extends ReadRoute {

    /**
     * Метод, реализующий логику команды update.
     *
     * @param collection коллекция, в которой происходит обновление
     * @param id         id элемента, который нужно обновить
     * @param value      строковое представление нового элемента
     * @param parse      нужно ли парсить строку в элемент
     * @throws InvalidNameException      если имя элемента некорректно
     * @throws WrongArgumentsException   если аргументы команды введены неверно
     * @throws InvalidDistanceException  если дистанция элемента некорректна
     */
    public void mainMethod(Stack<Route> collection, long id, String value, boolean parse)
            throws InvalidNameException, WrongArgumentsException, InvalidDistanceException, AbsentRequiredParametersException {
        boolean isFound = false;
        for (Route route : collection) {
            isFound |= route.getId() == id;
        }
        if (!isFound) {
            System.out.println("Элемент с id " + id + " не найден. Обновление не выполнено.");
            return;
        }
        Route newRoute = parse ? parseRoute(value) : readRoute(value);
        newRoute.setId(id);
        for (Route route : collection) {
            if (route.getId() == id) {
                collection.remove(route);
                collection.add(newRoute);
                System.out.println("Элемент с id " + id + " успешно обновлен.");
                break;
            }
        }
    }
}
