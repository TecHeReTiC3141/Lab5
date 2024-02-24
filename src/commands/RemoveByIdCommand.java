package commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.InputValidator;

/**
 * Класс, предназначенный для удаления элемента коллекции по его id.
 */

public class RemoveByIdCommand extends BaseCommand {

    public RemoveByIdCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, удаляющий элемент коллекции по его id.
     *
     * @param commandParts массив, содержащий название аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfOneArgument(commandParts);
            long id = Long.parseLong(commandParts[1]);
            System.out.println("Элемент с id " + id + (collection.removeIf(route -> route.getId() == id) ? " успешно удален" : " не найден"));
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID должен быть целым числом");
        }

    }
}
