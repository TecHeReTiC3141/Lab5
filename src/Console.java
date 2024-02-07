import exceptions.UnknownCommandException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

public class Console {
    private final RouteController controller = new RouteController();

    private final InputValidator validator = new InputValidator();

    private Date initDate = new Date();

    private boolean flag = true;

    public void run() {
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
            validator.checkIsValidCommand(commandParts[0]);
            switch (commandParts[0]) {
                case "exit":
                    try {
                        validator.checkIfOneArgument(commandParts);
                        flag = false;

                        System.out.println("Выход из программы. Сохранение коллекции...");
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "help":
                    try {
                        validator.checkIfOneArgument(commandParts);
                        controller.helpCommand.mainMethod();
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "info":
                    try {
                        validator.checkIfOneArgument(commandParts);
                        controller.infoCommand.mainMethod(collection, initDate);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
            }
        } catch (UnknownCommandException e) {
            System.out.println(e.getMessage());
        }

    }

}
