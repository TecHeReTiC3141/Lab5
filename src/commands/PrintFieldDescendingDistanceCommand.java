package commands;

import routeClasses.Route;
import routeClasses.RouteDistanceComparator;

import java.util.Stack;

public class PrintFieldDescendingDistanceCommand {

    public void mainMethod(Stack<Route> collection) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        System.out.println("Поля distance в порядке убывания:");
        collection.stream().sorted(new RouteDistanceComparator()).forEach(r -> System.out.printf("%s - %s%n", r.getId(), r.getDistance()));
    }
}
