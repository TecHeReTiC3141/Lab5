package commands;

import routeClasses.Route;

import java.util.Stack;

public class ClearCommand {

    public void mainMethod(Stack<Route> collection) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}
