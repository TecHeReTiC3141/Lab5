package commands;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import routeClasses.Coordinates;
import routeClasses.LocationFrom;
import routeClasses.LocationTo;
import routeClasses.Route;
import utils.InputValidator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
                    route.setName(InputValidator.checkName(keyValue[1]));
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

    public Route parseRoute(String line) throws InvalidNameException, InvalidDistanceException, WrongArgumentsException {
        String[] pairs = line.trim().replace('{', ' ').replace('}', ' ')
                .replace("\"", "").replace("'", "").split(",");
        Route route = new Route();
        LocationTo locationTo = new LocationTo();
        LocationFrom locationFrom = new LocationFrom();
        Coordinates coordinates = new Coordinates();
        ArrayList<String> requiredParams = new ArrayList<>(
                List.of("name, distance, coordinatesX, coordinatesY, creationDate, toX, toY, toZ".split(", "))
        );
        int addFrom = 0;

        DateTimeFormatter formatter = route.getDateFormat();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length != 2) {
                throw new WrongArgumentsException("Неверное количество параметров, проверьте, что у всех переданных параметров непустое значение");
            }
            String key = keyValue[0].trim(), value = keyValue[1].trim();
            switch (key) {
                case "id":
                    route.setId(Long.parseLong(value));
                    break;
                case "name":
                    InputValidator.checkName(value);
                    route.setName(value);
                    break;
                case "distance":
                    route.setDistance(InputValidator.checkDistance(value));
                    break;
                case "coordinatesX":
                    coordinates.setX(Long.parseLong(value));
                    break;
                case "coordinatesY":
                    coordinates.setY(Long.parseLong(value));
                    break;
                case "creationDate":
                    route.setCreationDate(ZonedDateTime.of(LocalDateTime.parse(value, formatter), ZoneId.of("UTC+3")));
                    break;
                case "toX":
                    locationTo.setX(Float.parseFloat(value));
                    break;
                case "toY":
                    locationTo.setY(Float.parseFloat(value));
                    break;
                case "toZ":
                    locationTo.setZ(Double.parseDouble(value));
                    break;
                case "toName":
                    locationTo.setName(value);
                    break;
                case "fromX":
                    locationFrom.setX(Integer.parseInt(value));
                    ++addFrom;
                    break;
                case "fromY":
                    locationFrom.setY(Long.parseLong(value));
                    ++addFrom;
                    break;
                case "fromZ":
                    locationFrom.setZ(Double.parseDouble(value));
                    ++addFrom;
                    break;
                default:
                    throw new WrongArgumentsException("Лишнее поле: " + keyValue[0]);
            }
            requiredParams.remove(key);
        }
        if (!requiredParams.isEmpty()) {
            throw new WrongArgumentsException("Не хватает обязательных параметров: " + requiredParams);
        }
        route.setCoordinates(coordinates);
        route.setTo(locationTo);
        if (addFrom == 3) {
            route.setFrom(locationFrom);
        }
        return route;
    }

    public Route readFromXML(Node routeNode) throws InvalidNameException, WrongArgumentsException, InvalidDistanceException {
        ArrayList<String> requiredParams = new ArrayList<>(
                List.of("name, distance, coordinates, creationDate, locationTo".split(", "))
        );

        Route route = new Route();
        DateTimeFormatter formatter = route.getDateFormat();

        NodeList children = routeNode.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            NamedNodeMap attributes = child.getAttributes();
            Node xNode, yNode, zNode;
            switch (child.getNodeName()) {
                case "#text":
                    break;
                case "id":
                    route.setId(Long.parseLong(attributes.getNamedItem("value").getNodeValue()));
                    break;
                case "name":
                    route.setName(InputValidator.checkName(attributes.getNamedItem("value").getNodeValue()));
                    break;
                case "coordinates":
                    route.setCoordinates(new Coordinates(
                            Long.parseLong(attributes.getNamedItem("x").getNodeValue()),
                            Long.parseLong(attributes.getNamedItem("y").getNodeValue())
                    ));
                    break;
                case "creationDate":
                    route.setCreationDate(ZonedDateTime.of(LocalDateTime.parse(
                            attributes.getNamedItem("value").getNodeValue(),
                            formatter), ZoneId.of("UTC+3")));
                    break;
                case "locationFrom":
                    xNode = attributes.getNamedItem("x");
                    yNode = attributes.getNamedItem("y");
                    zNode = attributes.getNamedItem("z");
                    if (xNode == null || yNode == null || zNode == null) {
                        break;
                    }
                    route.setFrom(new LocationFrom(
                            Integer.parseInt(xNode.getNodeValue()),
                            Long.parseLong(yNode.getNodeValue()),
                            Double.parseDouble(zNode.getNodeValue())));
                    break;
                case "locationTo":
                    xNode = attributes.getNamedItem("x");
                    yNode = attributes.getNamedItem("y");
                    zNode = attributes.getNamedItem("z");
                    Node nameNode = attributes.getNamedItem("name");
                    if (xNode == null || yNode == null || zNode == null || nameNode == null) {
                        throw new WrongArgumentsException("В поле locationTo не хватает обязательных атрибутов");
                    }
                    route.setTo(new LocationTo(
                            Float.parseFloat(xNode.getNodeValue()),
                            Float.parseFloat(yNode.getNodeValue()),
                            Float.parseFloat(zNode.getNodeValue()),
                            nameNode.getNodeValue()));
                    break;
                case "distance":
                    route.setDistance(InputValidator.checkDistance(attributes.getNamedItem("value").getNodeValue()));
                    break;
                default:
                    throw new WrongArgumentsException("Лишнее поле: " + child.getNodeName());

            }
            requiredParams.remove(child.getNodeName());
        }
        if (!requiredParams.isEmpty()) {
            throw new WrongArgumentsException("Не хватает обязательных параметров: " + requiredParams);
        }
        return route;
    }
}
