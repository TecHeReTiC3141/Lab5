import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentsException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import routeClasses.Route;
import utils.InputValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

public class Console {
    private final RouteController controller = new RouteController();

    private Date initDate = new Date();

    private final String dataFile = System.getenv("DATA_FILE");

    private boolean flag = true;

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
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(dataFile));

            Stack<Route> collection = controller.getRoutes();

            NodeList routeElements = doc.getDocumentElement().getElementsByTagName("Route");
            for (int i = 0; i < routeElements.getLength(); ++i) {
                ++lineCount;
                Node route = routeElements.item(i);
                controller.addCommand.putToCollection(collection, controller.addCommand.readFromXML(route), true);
            }
//        int lineCounter = 0;
//        try (FileReader reader = new FileReader(dataFile)) {
//            Stack<Route> collection = controller.getRoutes();
//            String line = "";
//            while (reader.ready()) {
//                char c = (char) reader.read();
//                if (c == '\n') {
//                    lineCounter++;
//                    try {
//                        controller.addCommand.mainMethod(collection, line, true, true);
//                    } catch (InvalidNameException | InvalidDistanceException | WrongArgumentsException e) {
//                        System.err.printf("Ошибка в записи %s: %s%n", lineCounter, e.getMessage());
//                    } catch (NumberFormatException e) {
//                        System.err.printf("Ошибка в записи %s: неверный формат числа%n", lineCounter);
//                    }
//                    line = "";
//                } else {
//                    line += c;
//                }
//            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.exit(1);
        } catch (InvalidNameException | InvalidDistanceException | WrongArgumentsException e) {
            System.err.printf("Ошибка при чтении записи %s: %s%n", lineCount, e.getMessage());
        }
    }

    public void run() {
        loadInitialCollection();
        System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String line = scanner.nextLine();
            processCommand(line, 1);
        }
    }

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
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.helpCommand.mainMethod();
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "info":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.infoCommand.mainMethod(collection, initDate);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "show":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.showCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "add":
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        controller.addCommand.mainMethod(collection, commandParts[1], depth > 1, false);
                        break;
                    } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException e) {
                        System.err.println(e.getMessage());
                        return;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Должно быть ровно 2 начальных параметра (name и distance)");
                        return;
                    }
                case "remove_by_id":
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        controller.removeByIdCommand.mainMethod(collection, Integer.parseInt(commandParts[1]));
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    } catch (NumberFormatException e) {
                        System.err.println("ID должен быть целым числом");
                        return;
                    }
                case "clear":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.clearCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "save":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.saveCommand.mainMethod(dataFile, collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    } catch (ParserConfigurationException e) {
                        System.err.println("Ошибка при сохранение");
                        return;
                    }
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
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        controller.removeAtCommand.mainMethod(collection, Integer.parseInt(commandParts[1]));
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    } catch (NumberFormatException e) {
                        System.err.println("index должен быть целым числом");
                        return;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Элемента с таким индексом не существует. Проверьте, что это число больше 0 и меньше размера коллекции");
                    }
                case "reorder":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.reorderCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "sort":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.sortCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "count_greater_than_distance":
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        controller.countGreaterThanDistanceCommand.mainMethod(collection, Double.parseDouble(commandParts[1]));
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    } catch (NumberFormatException e) {
                        System.err.println("distance должен быть положительным вещественным числом");
                        return;
                    }
                case "print_ascending":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.printAscendingCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }
                case "print_field_descending_distance":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.printDescendingDistanceCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.err.println(e.getMessage());
                        return;
                    }

            }
        } catch (UnknownCommandException e) {
            System.err.println(e.getMessage());
        }

    }

}
