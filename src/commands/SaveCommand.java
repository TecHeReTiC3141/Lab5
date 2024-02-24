package commands;

import exceptions.WrongArgumentsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.CollectionManager;
import utils.InputValidator;

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
    public void execute(String[] commandParts) {

        try {
            InputValidator.checkIfNoArguments(commandParts);
            if (manager.getIsEmpty()) {
                System.out.println("Коллекция пуста, нечего сохранять.");
                return;
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = builder.newDocument();
            Element root = document.createElement("Routes");
            document.appendChild(root);
            manager.addCollectionToRoot(document, root);

        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка при сохранение");
        }


    }
}
