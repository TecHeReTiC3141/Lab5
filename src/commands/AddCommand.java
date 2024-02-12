package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, предоставляющий метод для добавления элемента в коллекцию.
 * Элемент может быть как добавлен пользователем вручную, так и считан из файла или из строки скрипта.
 */
public class AddCommand extends ReadRoute {

    /**
     * Метод, добавляющий route в коллекцию и устанавливающий id элемента при необходимости.
     *
     * @param collection коллекция, в которую нужно добавить элемент
     * @param route      элемент, который нужно добавить
     * @param silence    флаг, определяющий, нужно ли выводить сообщения о результате выполнения метода
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
     * @param collection коллекция, в которую нужно добавить элемент
     * @param value      строка, содержащая информацию об элементе
     * @param parse      флаг, определяющий, нужно ли парсить строку
     * @param silence    флаг, определяющий, нужно ли выводить сообщения о результате выполнения метода
     * @throws InvalidNameException     the invalid name exception
     * @throws InvalidDistanceException the invalid distance exception
     * @throws WrongArgumentsException  the wrong arguments exception
     * @throws AbsentRequiredParametersException the absent required parameters exception
     */

    public void mainMethod(Stack<Route> collection, String value, boolean parse, boolean silence)
            throws InvalidNameException, InvalidDistanceException, WrongArgumentsException, AbsentRequiredParametersException {
        Route route = parse ? parseRoute(value) : readRoute(value);
        putToCollection(collection, route, silence);
    }
}
