package commands;

import routeClasses.Route;

import java.util.Stack;

public class ShowCommand {

    public void mainMethod(Stack<Route> collection) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        System.out.println("Содержимое коллекции:");
        for (Route route : collection) {
            System.out.println(route);
        }
    }
}
