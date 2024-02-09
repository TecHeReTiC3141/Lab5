package commands;

import routeClasses.Route;

import java.util.Stack;

public class RemoveByIdCommand {

    public void mainMethod(Stack<Route> collection, int id) {
        System.out.println("Элемент с id " + id + (collection.removeIf(route -> route.getId() == id) ? " успешно удален" : " не найден"));
    }
}
