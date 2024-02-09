package commands;

import routeClasses.Route;

import java.util.ArrayList;
import java.util.Stack;

public class SortCommand {

    public void mainMethod(Stack<Route> collection) {
//        collection.sort(Route::compareTo);
        ArrayList<Route> routes = new ArrayList<>(collection);
        routes.sort(Route::compareTo);
        collection.clear();
        for (Route route : routes) {
            collection.push(route);
        }
        System.out.println("Коллекция успешно отсортирована");
    }
}
