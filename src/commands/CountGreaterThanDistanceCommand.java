package commands;

import routeClasses.Route;

import java.util.Stack;

public class CountGreaterThanDistanceCommand {

    public void mainMethod(Stack<Route> collection, double distance) {
        long count = collection.stream().filter(route -> route.getDistance() > distance).count();
        System.out.printf("Количество элементов, значение поля distance которых больше %s - %s%n", distance, count);
    }
}
