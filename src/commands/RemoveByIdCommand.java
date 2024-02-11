package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, предназначенный для удаления элемента коллекции по его id.
 */

public class RemoveByIdCommand {

    /**
     * Метод, удаляющий элемент коллекции по его id.
     *
     * @param collection коллекция, из которой удаляется элемент
     * @param id         id элемента, который нужно удалить
     */

    public void mainMethod(Stack<Route> collection, int id) {
        System.out.println("Элемент с id " + id + (collection.removeIf(route -> route.getId() == id) ? " успешно удален" : " не найден"));
    }
}
