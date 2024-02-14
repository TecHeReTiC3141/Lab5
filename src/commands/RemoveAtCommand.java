package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду remove_at, удаляющую элемент коллекции по его индексу.
 */

public class RemoveAtCommand extends BaseCommand {

    public RemoveAtCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Метод, реализующий логику команды remove_at.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfOneArgument(commandParts);
            collection.removeElementAt(Integer.parseInt(commandParts[1]));
            System.out.println("Элемент успешно удален");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("index должен быть целым числом");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Элемента с таким индексом не существует. Проверьте, что это число больше 0 и меньше размера коллекции");
        }
    }
}
