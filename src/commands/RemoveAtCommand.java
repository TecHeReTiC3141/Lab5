package commands;

import routeClasses.Route;

import java.util.Stack;

public class RemoveAtCommand {

    public void mainMethod(Stack<Route> collection, int index) {
        collection.removeElementAt(index);
        System.out.println("Элемент успешно удален");

    }
}
