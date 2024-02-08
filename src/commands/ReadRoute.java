package commands;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Coordinates;
import routeClasses.LocationFrom;
import routeClasses.LocationTo;
import routeClasses.Route;
import utils.InputValidator;

import java.time.ZonedDateTime;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите координату X - целое число: ");
                String line = scanner.nextLine();
                coordinates.setX(Long.parseLong(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата X должна быть целым числом");
            }
        }
        while (true) {
            try {
                System.out.println("Введите координату Y - целое число: ");
                String line = scanner.nextLine();
                coordinates.setY(Long.parseLong(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата Y должна быть целым числом");
            }
        }
        while (true) {
            try {
                System.out.println("Введите название пункта прибытия (To): ");
                String line = scanner.nextLine();
                InputValidator.checkName(line);
                locationTo.setName(line);
                break;
            } catch (InvalidNameException e) {
                System.err.println("Название пункта прибытия не может быть пустым");
            }
        }
        while (true) {
            try {
                System.out.println("Введите координату X пункта прибытия (To) - вещественное число: ");
                String line = scanner.nextLine();
                locationTo.setX(Float.parseFloat(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата должна быть вещественным числом");
            }
        }
        while (true) {
            try {
                System.out.println("Введите координату Y пункта прибытия (To) - вещественное число: ");
                String line = scanner.nextLine();
                locationTo.setY(Float.parseFloat(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата должна быть вещественным числом");
            }
        }
        while (true) {
            try {
                System.out.println("Введите координату Z пункта прибытия (To) - вещественное число: ");
                String line = scanner.nextLine();
                locationTo.setZ(Double.parseDouble(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата должна быть вещественным числом");
            }
        }
        boolean toHasName;
        System.out.println("Есть ли название пункта прибытия (To)? (yes/no)");
        while (true) {
            try {
                String line = scanner.nextLine().trim().toLowerCase();
                InputValidator.checkIfYesOrNo(line);
                toHasName = line.equals("yes");
                break;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        if (toHasName) {
            while (true) {
                try {
                    System.out.println("Введите название пункта отправления: ");
                    String line = scanner.nextLine().trim();
                    InputValidator.checkName(line);
                    locationTo.setName(line);
                    break;
                } catch (InvalidNameException e) {
                    System.err.println("Название пункта прибытия не может быть пустым");
                }
            }
        } else {
            locationTo.setName("null");
        }
        boolean hasFrom;
        System.out.println("Будет ли у маршрута пункт отправления (From)? (yes/no)");
        while (true) {
            try {
                String line = scanner.nextLine().trim().toLowerCase();
                InputValidator.checkIfYesOrNo(line);
                hasFrom = line.equals("yes");
                break;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        if (hasFrom) {
            while (true) {
                try {
                    System.out.println("Введите координату X пункта отправления (From) - целое число: ");
                    String line = scanner.nextLine();
                    locationFrom.setX(Integer.parseInt(line));
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Координата должна быть целым числом");
                }
            }
            while (true) {
                try {
                    System.out.println("Введите координату Y пункта отправления (From) - целое число: ");
                    String line = scanner.nextLine();
                    locationFrom.setY(Long.parseLong(line));
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Координата должна быть целым числом");
                }
            }
            while (true) {
                try {
                    System.out.println("Введите координату Z пункта отправления (From) - вещественное число: ");
                    String line = scanner.nextLine();
                    locationFrom.setZ(Double.parseDouble(line));
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Координата должна быть вещественным числом");
                }
            }
            route.setFrom(locationFrom);
        }
        route.setCreationDate(ZonedDateTime.now());
        route.setCoordinates(coordinates);
        route.setTo(locationTo);

        return route;
    }
}
