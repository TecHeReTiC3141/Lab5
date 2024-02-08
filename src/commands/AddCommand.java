package commands;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;

import java.util.Stack;

public class AddCommand extends ReadRoute {

    public void mainMethod(Stack<Route> collection, String value)
            throws InvalidNameException, InvalidDistanceException, WrongArgumentsException {
        Route route = readRoute(value);
        if (collection.isEmpty()) {
            route.setId(1);
        } else {
            long maxId = 0L;
            for (Route item : collection) {
                maxId = Math.max(maxId, item.getId());
            }
            route.setId(maxId + 1);
        }

        collection.push(route);
        System.out.println("Маршрут успешно добавлен в коллекцию");
    }
}
