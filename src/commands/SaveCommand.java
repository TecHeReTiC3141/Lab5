package commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import routeClasses.Route;

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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Stack;

/**
 * Класс, реализующий команду save, которая сохраняет коллекцию в файл.
 */

public class SaveCommand {

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
            NodeList nodeList = (NodeList)expr.evaluate(node, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node nd = nodeList.item(i);
                nd.getParentNode().removeChild(nd);
            }

            Transformer transformer =  TransformerFactory.newInstance().newTransformer();
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
     * Метод, сохраняющий коллекцию в файл.
     *
     * @param filename   имя файла, в который будет сохранена коллекция
     * @param collection коллекция, которая будет сохранена
     * @throws ParserConfigurationException исключение, возникающее при работе с xml
     */
    public void mainMethod(String filename, Stack<Route> collection) throws ParserConfigurationException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = builder.newDocument();
        Element root = document.createElement("Routes");
        document.appendChild(root);

        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста, нечего сохранять.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Route route : collection) {
                Element newRouteRoot = document.createElement("Route");
                route.appendNode(document, newRouteRoot);
                root.appendChild(newRouteRoot);
            }
            System.out.println("Коллекция успешно сохранена в файл " + filename);
            writer.write(nodeToString(document, false, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
