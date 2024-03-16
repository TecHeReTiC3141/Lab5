package server.commands;

import routeClasses.Route;
import utils.CollectionManager;

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

    public String execute(String[] commandParts, Route route) {
        return manager.putToCollection(route, false);
    }

}
