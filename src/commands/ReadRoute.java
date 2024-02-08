package commands;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Coordinates;
import routeClasses.LocationFrom;
import routeClasses.LocationTo;
import routeClasses.Route;
import utils.InputValidator;

/**
 * Абстрактный класс, предоставляющий метод для чтения маршрута из строки.
 */
public abstract class ReadRoute {

    public Route readRoute(String value) throws InvalidNameException, InvalidDistanceException, WrongArgumentsException {
        Route route = new Route();
        LocationTo locationTo = new LocationTo();
        LocationFrom locationFrom = new LocationFrom();
        Coordinates coordinates = new Coordinates();

        String[] initParams = value.trim()
                .replace("{", "")
                .replace("}", "")
                .replace(" ", "")
                .replace("\"", "").split(",");
        if (initParams.length != 2) {
            throw new ArrayIndexOutOfBoundsException("Неверное количество параметров");
        }
        for (String pair : initParams) {
            String[] keyValue = pair.split("=");

            switch (keyValue[0]) {
                case "name":
                    InputValidator.checkName(keyValue[1]);
                    route.setName(keyValue[1]);
                    break;
                case "distance":
                    route.setDistance(InputValidator.checkDistance(keyValue[1]));
                    break;
                default:
                    throw new WrongArgumentsException("Неверный начальный параметр: " + keyValue[0]);
            }
        }

        return route;
    }
}
