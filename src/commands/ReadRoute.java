package commands;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import routeClasses.Coordinates;
import routeClasses.LocationFrom;
import routeClasses.LocationTo;
import routeClasses.Route;
import utils.InputValidator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Абстрактный класс, предоставляющий метод для чтения маршрута из строки.
 */
public abstract class ReadRoute extends BaseCommand {

    public ReadRoute(String name, String description, Stack<Route> collection) {
        super(name, description, collection, true);
    }

    /**
     * Метод для чтения маршрута из ввода пользователя
     * (нужно сначала ввести name и distance, затем будут запрошены остальные данные).
     *
     * @return прочитанный объект класса Route
     * @throws InvalidNameException     если имя маршрута некорректно
     * @throws InvalidDistanceException если дистанция маршрута некорректна
     * @throws WrongArgumentsException  если введены некорректные аргументы
     */

    public Route readRoute() throws InvalidNameException, InvalidDistanceException, WrongArgumentsException {
        Route route = new Route();
        LocationTo locationTo = new LocationTo();
        LocationFrom locationFrom = new LocationFrom();
        Coordinates coordinates = new Coordinates();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите name - не пустую строку: ");
                String line = scanner.nextLine();
                route.setName(InputValidator.checkName(line));
                break;
            } catch (InvalidNameException e) {
                System.err.println("Поле name должно быть непустой строкой");
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
            }
        }
        while (true) {
            try {
                System.out.println("Введите distance - вещественное число > 1: ");
                String line = scanner.nextLine();
                route.setDistance(InputValidator.checkDistance(line));
                break;
            } catch (InvalidDistanceException e) {
                System.err.println("Поле distance должно быть вещественным числом больше 1");
            } catch (NumberFormatException e) {
                System.err.println("Введите вещественное число");
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
            }
        }
        while (true) {
            try {
                System.out.println("Введите координату X - целое число: ");
                String line = scanner.nextLine();
                coordinates.setX(Long.parseLong(line));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Координата X должна быть целым числом");
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
                } catch (NoSuchElementException e) {
                    System.err.println("Выход из программы...");
                    System.exit(130);
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
            } catch (NoSuchElementException e) {
                System.err.println("Выход из программы...");
                System.exit(130);
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
                } catch (NoSuchElementException e) {
                    System.err.println("Выход из программы...");
                    System.exit(130);
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
                } catch (NoSuchElementException e) {
                    System.err.println("Выход из программы...");
                    System.exit(130);
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
                } catch (NoSuchElementException e) {
                    System.err.println("Выход из программы...");
                    System.exit(130);
                }
            }
            route.setFrom(locationFrom);
        }
        route.setCreationDate(ZonedDateTime.now());
        route.setCoordinates(coordinates);
        route.setTo(locationTo);

        return route;
    }

    /**
     * Метод для чтения маршрута из строки, содержащей все необходимые поля маршрута.
     * Используется при чтении add и update команд, запущенные в execute_script.
     *
     * @param line строка, содержащая значения полей маршрута
     * @return прочитанный объект класса Route
     * @throws InvalidNameException              если имя маршрута некорректно
     * @throws InvalidDistanceException          если дистанция маршрута некорректна
     * @throws WrongArgumentsException           если введены некорректные аргументы
     * @throws AbsentRequiredParametersException если не хватает обязательных параметров
     */


    public Route parseRoute(String line) throws InvalidNameException, InvalidDistanceException, WrongArgumentsException, AbsentRequiredParametersException {
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
            throw new AbsentRequiredParametersException("Не хватает обязательных параметров: " + requiredParams);
        }
        route.setCoordinates(coordinates);
        route.setTo(locationTo);
        if (addFrom == 3) {
            route.setFrom(locationFrom);
        }
        return route;
    }

}
