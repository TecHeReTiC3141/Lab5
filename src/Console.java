import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

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
        int lineCounter = 0;
        try (FileReader reader = new FileReader(dataFile)) {
            Stack<Route> collection = controller.getRoutes();
            String line = "";
            while (reader.ready()) {
                char c = (char) reader.read();
                if (c == '\n') {
                    lineCounter++;
                    try {
                        controller.addCommand.mainMethod(collection, line, true);
                    } catch (InvalidNameException | InvalidDistanceException | WrongArgumentsException e) {
                        System.err.println("Ошибка в строке " + lineCounter + ": " + e.getMessage());
                    }
                    line = "";
                } else {
                    line += c;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.exit(1);
        }
    }

    public void run() {
        loadInitialCollection();
        System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            String line = scanner.nextLine();
            processCommand(line);
        }
    }

    public void processCommand(String line) {

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
                        controller.addCommand.mainMethod(collection, commandParts[1]);
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
                    }
                case "exit":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        flag = false;

                        System.out.println("Выход из программы. Сохранение коллекции...");
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
