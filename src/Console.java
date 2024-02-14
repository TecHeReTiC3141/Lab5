import exceptions.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import routeClasses.Route;
import utils.InputValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс, отвечающий за работу консоли, предоставляющей пользователю доступ к коллекции посредством команд
 */

public class Console {
    /**
     * Контроллер, содержащий все команду и коллекцию.
     */
    private final RouteController controller = new RouteController();
    /**
     * Дата инициализации приложения и коллекции
     */
    private Date initDate = new Date();
    /**
     * Путь к файлу, в котором хранится коллекция и куда она сохраняется
     */
    private final String dataFile = System.getenv("DATA_FILE");

    /**
     * Флаг, указывающий, продолжает ли работать приложение
     */
    private boolean flag = true;

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
            Stack<Route> collection = controller.getRoutes();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(reader));
            NodeList routeElements = doc.getDocumentElement().getElementsByTagName("Route");
            for (int i = 0; i < routeElements.getLength(); ++i) {
                ++lineCount;
                Node route = routeElements.item(i);
                try {
                    controller.addCommand.putToCollection(collection, controller.addCommand.readFromXML(route), true);
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
     * Метод, запускающий цикл работы консольного приложения из следующих шагов:
     * 1. Загрузка начальной коллекции
     * 2. Вывод приветствия и предложение ввести help
     * 3. Чтение и обработка команды из консоли
     */
    public void run() {
        loadInitialCollection();
        System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String line = scanner.nextLine();
            processCommand(line, 1);
        }
    }

    /**
     * Метод, обрабатывающий команду, введенную пользователем
     *
     * @param line  строка, содержащая команду
     * @param depth глубина рекурсии, используется для предотвращения бесконечной рекурсии при вызове execute_script
     */

    public void processCommand(String line, int depth) {
        if (depth > 1000) {
            System.out.println("Превышена максимальная глубина рекурсии, вероятно, из-за рекурсивного вызова execute_script, проверьте скрипт на вызов самого себя");
            return;
        }

        String[] commandParts = line.trim().split(" ");

        if (commandParts.length == 0) {
            return;
        }

        Stack<Route> collection = controller.getRoutes();
        try {
            InputValidator.checkIsValidCommand(commandParts[0]);
            switch (commandParts[0]) {
                case "help":
                    controller.helpCommand.execute(commandParts);
                    break;
                case "info":
                    controller.infoCommand.execute(commandParts);
                    break;

                case "show":
                    controller.showCommand.execute(commandParts);
                    break;

                case "add":
                    controller.addCommand.execute(commandParts, depth > 1);
                    break;

                case "update":
                    controller.updateByIdCommand.execute(commandParts, depth > 1);
                    break;

                case "remove_by_id":
                    controller.removeByIdCommand.execute(commandParts);
                    break;

                case "clear":
                    controller.clearCommand.execute(commandParts);
                    break;

                case "save":
                    controller.saveCommand.execute(commandParts);
                    break;

                case "execute_script":
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        String filename = commandParts[1];
                        try (FileReader reader = new FileReader(filename)) {
                            Scanner scanner = new Scanner(reader);
                            while (scanner.hasNextLine()) {
                                processCommand(scanner.nextLine(), depth + 1);
                            }
                        } catch (IOException e) {
                            System.err.println("Ошибка при чтении файла: " + e.getMessage());
                        }

                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "exit":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        flag = false;
                        System.out.println("Выход из программы...");
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "remove_at":
                    controller.removeAtCommand.execute(commandParts);
                    break;

                case "reorder":
                    controller.reorderCommand.execute(commandParts);
                    break;

                case "sort":
                    controller.sortCommand.execute(commandParts);
                    break;

                case "count_greater_than_distance":
                    controller.countGreaterThanDistanceCommand.execute(commandParts);
                    break;

                case "print_ascending":
                    controller.printAscendingCommand.execute(commandParts);
                    break;

                case "print_field_descending_distance":
                    controller.printDescendingDistanceCommand.execute(commandParts);
                    break;
            }
        } catch (UnknownCommandException e) {
            System.err.println(e.getMessage());
        }
    }

}
