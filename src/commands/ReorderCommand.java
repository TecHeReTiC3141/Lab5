package commands;

import routeClasses.Route;

import java.util.Stack;

public class ReorderCommand {

    public void mainMethod(Stack<Route> collection) {
        Stack<Route> temp = new Stack<>();
        while (!collection.isEmpty()) {
            temp.push(collection.pop());
        }

        for (Route route : temp) {
            collection.push(route);
        }
        System.out.println("Порядок элементов коллекции изменен на обратный");
    }
}
