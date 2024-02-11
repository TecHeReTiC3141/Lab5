package commands;

import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, реализующий команду remove_at, удаляющую элемент коллекции по его индексу.
 */

public class RemoveAtCommand {

    /**
     * Метод, реализующий логику команды remove_at.
     *
     * @param collection коллекция, из которой удаляется элемент
     * @param index      индекс элемента, который нужно удалить
     */

    public void mainMethod(Stack<Route> collection, int index) {
        collection.removeElementAt(index);
        System.out.println("Элемент успешно удален");

    }
}
