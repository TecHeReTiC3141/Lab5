package commands;

import routeClasses.Route;

import java.util.Stack;

public class PrintAscendingCommand {

    public void mainMethod(Stack<Route> collection) {
        collection.stream()
                .sorted()
                .forEach(System.out::println);
    }
}
