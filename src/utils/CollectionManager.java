package utils;

import exceptions.AbsentRequiredParametersException;
import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.WrongArgumentsException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import routeClasses.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Класс, отвечающий за работу коллекции
 */

public class CollectionManager {

    /**
     * Дата инициализации коллекции
     */
    private final Date initDate = new Date();

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
                    putToCollection(newRoute, true);
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
     * Метод, возвращающий, пуста ли коллекция
     */
    public boolean getIsEmpty() {
        return collection.isEmpty();
    }


    /**
     * Метод, добавляющий route в коллекцию и устанавливающий id элемента при необходимости.
     *
     * @param route   элемент, который нужно добавить
     * @param silence флаг, указывающий, нужно ли выводить сообщение о добавлении элемента
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

    /**
     * Метод, очищающий коллекцию.
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Метод, возвращающий количество элементов коллекции, значение поля distance которых больше заданного.
     *
     * @param distance значение поля distance
     * @return количество элементов коллекции, значение поля distance которых больше заданного
     */
    public long countGreaterThanDistance(double distance) {
        return collection.stream().filter(route -> route.getDistance() > distance).count();
    }

    /**
     * Метод, возвращающий название класса коллекции.
     *
     * @return название класса коллекции
     */
    public String getCollectionClassName() {
        return collection.getClass().getName();
    }

    /**
     * Метод, возвращающий дату инициализации коллекции.
     *
     * @return дата инициализации коллекции
     */

    public Date getInitDate() {
        return initDate;
    }

    /**
     * Метод, возвращающий количество элементов в коллекции.
     *
     * @return количество элементов в коллекции
     */

    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * Метод, выводящий элементы коллекции в порядке возрастания.
     */
    public void printAscendingCommand() {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        collection.stream()
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Метод, выводящий поле distance элементов коллекции в порядке убывания.
     */
    public void printDescendingDistance() {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        System.out.println("Поля distance в порядке убывания:");
        collection.stream().sorted(new RouteDistanceComparator()).forEach(r -> System.out.printf("%s - %s%n", r.getId(), r.getDistance()));
    }

    /**
     * Метод, удаляющий элемент коллекции по его индексу в коллекции.
     *
     * @param index индекс элемента, который нужно удалить
     */

    public void removeElementAt(int index) {
        if (index < 0 || index >= collection.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        collection.remove(index);
        System.out.println("Элемент успешно удален");
    }

    /**
     * Метод, удаляющий элемент коллекции по его id.
     *
     * @param id id элемента, который нужно удалить
     */

    public void removeById(long id) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        boolean removed = collection.removeIf(route -> route.getId() == id);
        if (removed) {
            System.out.println("Элемент успешно удален");
        } else {
            System.out.println("Элемент с таким id не найден");
        }
    }

    /**
     * Метод, меняющий порядок элементов коллекции на обратный.
     */
    public void reorder() {
        Stack<Route> temp = new Stack<>();
        while (!collection.isEmpty()) {
            temp.push(collection.pop());
        }

        for (Route route : temp) {
            collection.push(route);
        }
        System.out.println("Порядок элементов коллекции изменен на обратный");
    }

    /**
     * Метод, преобразующий объект типа Node в строку.
     *
     * @param node               объект типа Node
     * @param omitXmlDeclaration true, если необходимо убрать xml-заголовок
     * @param prettyPrint        true, если необходимо красиво оформить xml
     * @return строковое представление объекта типа Node
     */

    public static String nodeToString(Node node, boolean omitXmlDeclaration, boolean prettyPrint) {
        if (node == null) {
            throw new IllegalArgumentException("node is null.");
        }

        try {
            node.normalize();
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//text()[normalize-space()='']");
            NodeList nodeList = (NodeList) expr.evaluate(node, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node nd = nodeList.item(i);
                nd.getParentNode().removeChild(nd);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            if (omitXmlDeclaration) {
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }

            if (prettyPrint) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            }

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException | XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод, добавляющий коллекцию в корень xml документа.
     *
     * @param document объект типа Document
     * @param root     корневой элемент документа
     */
    public void addCollectionToRoot(Document document, Element root) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Route route : collection) {
                Element newRouteRoot = document.createElement("Route");
                route.appendNode(document, newRouteRoot);
                root.appendChild(newRouteRoot);
            }
            System.out.println("Коллекция успешно сохранена в файл " + dataFile);
            writer.write(nodeToString(document, false, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод, выводящий коллекцию.
     */

    public void showCollection() {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        System.out.println("Содержимое коллекции:");
        for (Route route : collection) {
            System.out.println(route);
        }
    }

    /**
     * Метод, сортирующий коллекцию в естественном порядке.
     */
    public void sortCollection() {
        ArrayList<Route> routes = new ArrayList<>(collection);
        routes.sort(Route::compareTo);
        collection.clear();
        for (Route route : routes) {
            collection.push(route);
        }
        System.out.println("Коллекция успешно отсортирована");
    }

    /**
     * Метод, проверяющий, есть ли элемент с данным id в коллекции.
     *
     * @param id id искомого элемента
     * @return true, если элемент найден, иначе - false
     */

    public boolean findElementById(long id) {
        for (Route route : collection) {
            if (route.getId() == id) {
                return true;
            }
        }
        return false;
    }


    /**
     * Метод, обновляющий элемент коллекции по его id, заменяя его другим элементом.
     *
     * @param id       id элемента, который нужно обновить
     * @param newRoute новый элемент
     */
    public void updateElementById(long id, Route newRoute) {
        for (Route route : collection) {
            if (route.getId() == id) {
                collection.remove(route);
                collection.add(newRoute);
                System.out.println("Элемент с id " + id + " успешно обновлен.");
                break;
            }
        }
    }

}
