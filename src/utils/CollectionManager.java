package utils;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import routeClasses.Coordinates;
import routeClasses.LocationFrom;
import routeClasses.LocationTo;
import routeClasses.Route;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Класс, отвечающий за работу коллекции
 */

public class CollectionManager {

    private final Stack<Route> collection;

    /**
     * Путь к файлу, в котором хранится коллекция и куда она сохраняется
     */
    private final String dataFile = System.getenv("DATA_FILE");

    public CollectionManager(Stack<Route> collection) {
        this.collection = collection;
    }


    /**
     * Метод для чтения маршрута из XML-файла.
     * Используется для изначального чтения маршрутов из файла из системной переменной DATA_FILE.
     *
     * @param routeNode узел XML-файла, содержащий информацию о маршруте
     * @return прочитанный объект класса Route
     * @throws InvalidNameException              если имя маршрута некорректно
     * @throws InvalidDistanceException          если дистанция маршрута некорректна
     * @throws WrongArgumentsException           если есть некорректные аргументы
     * @throws AbsentRequiredParametersException если не хватает обязательных параметров
     */

    public Route readFromXML(Node routeNode) throws InvalidNameException, WrongArgumentsException, InvalidDistanceException, AbsentRequiredParametersException {
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
                    if (xNode == null || yNode == null || zNode == null) {
                        throw new WrongArgumentsException("В поле locationTo не хватает обязательных атрибутов");
                    }
                    route.setTo(new LocationTo(
                            Float.parseFloat(xNode.getNodeValue()),
                            Float.parseFloat(yNode.getNodeValue()),
                            Float.parseFloat(zNode.getNodeValue()),
                            nameNode != null ? nameNode.getNodeValue() : "null"));
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
            throw new AbsentRequiredParametersException("Не хватает обязательных параметров: " + requiredParams);
        }
        return route;
    }

    /**
     * Метод, загружающий начальную коллекцию из файла в переменной окружения DATA_FILE
     */
    public void loadInitialCollection() {
        if (dataFile == null) {
            System.err.println("Не найдена переменная окружения DATA_FILE");
            System.exit(1);
        }
        int lastDotIndex = dataFile.lastIndexOf('.');
        if (lastDotIndex == -1 || !dataFile.substring(lastDotIndex + 1).equals("xml")) {
            System.err.println("Файл должен иметь расширение .xml");
            System.exit(1);
        }
        int lineCount = 0;

        try (FileReader reader = new FileReader(dataFile)) {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(reader));
            NodeList routeElements = doc.getDocumentElement().getElementsByTagName("Route");
            for (int i = 0; i < routeElements.getLength(); ++i) {
                ++lineCount;
                Node route = routeElements.item(i);
                try {
                    Route newRoute = readFromXML(route);
                    if (newRoute.getId() == 0) {
                        if (collection.isEmpty()) {
                            newRoute.setId(1);
                            continue;
                        }
                        long maxId = 0L;
                        for (Route item : collection) {
                            maxId = Math.max(maxId, item.getId());
                        }
                        newRoute.setId(maxId + 1);
                    }
                    collection.push(newRoute);
                } catch (InvalidNameException | InvalidDistanceException | WrongArgumentsException |
                         AbsentRequiredParametersException e) {
                    System.err.printf("Ошибка при чтении записи %s: %s%n", lineCount, e.getMessage());
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.exit(1);
        }
    }


    /**
     * Метод, добавляющий route в коллекцию и устанавливающий id элемента при необходимости.
     *
     * @param route      элемент, который нужно добавить
     * @param silence    флаг, указывающий, нужно ли выводить сообщение о добавлении элемента
     */

    public void putToCollection(Route route, boolean silence) {
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
}
