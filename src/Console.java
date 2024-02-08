import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

public class Console {
    private final RouteController controller = new RouteController();

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
            InputValidator.checkIsValidCommand(commandParts[0]);
            switch (commandParts[0]) {
                case "exit":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        flag = false;

                        System.out.println("Выход из программы. Сохранение коллекции...");
                        break;
                    } catch (WrongArgumentsException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "help":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.helpCommand.mainMethod();
                        break;
                    } catch (WrongArgumentsException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "info":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.infoCommand.mainMethod(collection, initDate);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "show":
                    try {
                        InputValidator.checkIfNoArguments(commandParts);
                        controller.showCommand.mainMethod(collection);
                        break;
                    } catch (WrongArgumentsException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                case "add":
                    try {
                        InputValidator.checkIfOneArgument(commandParts);
                        controller.addCommand.mainMethod(collection, commandParts[1]);
                        break;
                    } catch (WrongArgumentsException | InvalidNameException | InvalidDistanceException e) {
                        System.out.println(e.getMessage());
                        return;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Должно быть ровно 2 начальных параметра (name и distance)");
                        return;
                    }

            }
        } catch (UnknownCommandException e) {
            System.out.println(e.getMessage());
        }

    }

}
