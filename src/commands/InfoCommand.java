package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Date;
import java.util.Stack;

/**
 * Класс, реализующий команду info, выводящую информацию о коллекции (тип, дата инициализации, количество элементов).
 */

public class InfoCommand extends BaseCommand {

    /**
     * Дата инициализации приложения и коллекции
     */
    private final Date initDate = new Date();


    public InfoCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, реализующий логику команды info
     *
     * @param commandParts название и аргументы команды
     */
    public void execute(String [] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            System.out.printf("Тип коллекции: %s%n", collection.getClass().getName());
            System.out.printf("Дата инициализации: %s%n", initDate);
            System.out.printf("Количество элементов: %s%n", collection.size());        }
        catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
