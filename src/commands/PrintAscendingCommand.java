package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду print_ascending, которая выводит элементы коллекции, отсортированные в естественном порядке.
 */
public class PrintAscendingCommand extends BaseCommand {

    public PrintAscendingCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, реализующий логику команды print_ascending.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            if (collection.isEmpty()) {
                System.out.println("Коллекция пуста");
                return;
            }
            collection.stream()
                    .sorted()
                    .forEach(System.out::println);
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
