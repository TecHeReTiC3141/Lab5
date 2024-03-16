package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import routeClasses.Route;
import utils.CollectionManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Класс, реализующий команду save, которая сохраняет коллекцию в файл.
 */

public class SaveCommand extends BaseCommand {

    private final String dataFile = System.getenv("DATA_FILE");

    public SaveCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, сохраняющий коллекцию в файл.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */
    public String execute(String[] commandParts, Route route) {

        try {
            if (manager.getIsEmpty()) {
                return "Коллекция пуста, нечего сохранять.";
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = builder.newDocument();
            Element root = document.createElement("Routes");
            document.appendChild(root);
            manager.addCollectionToRoot(document, root);

        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка при сохранение");
        }
        return "";


    }
}
