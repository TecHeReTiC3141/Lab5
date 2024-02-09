package commands;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;

import java.util.Stack;

public class AddCommand extends ReadRoute {

    public void putToCollection(Stack<Route> collection, Route route, boolean silence) {
        if (route.getId() == 0) {

            if (collection.isEmpty()) {
                route.setId(1);
            } else {
                long maxId = 0L;
                for (Route item : collection) {
                    maxId = Math.max(maxId, item.getId());
                }
                route.setId(maxId + 1);
            }
        }

        collection.push(route);
        if (!silence) System.out.println("Маршрут успешно добавлен в коллекцию");
    }

    public void mainMethod(Stack<Route> collection, String value, boolean parse, boolean silence)
            throws InvalidNameException, InvalidDistanceException, WrongArgumentsException {
        Route route = parse ? parseRoute(value) : readRoute(value);
        putToCollection(collection, route, silence);
    }
}
