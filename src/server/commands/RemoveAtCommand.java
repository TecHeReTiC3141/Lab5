package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду remove_at, удаляющую элемент коллекции по его индексу.
 */

public class RemoveAtCommand extends BaseCommand {

    public RemoveAtCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды remove_at.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public void execute(String[] commandParts) {
        try {
            manager.removeElementAt(Integer.parseInt(commandParts[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Элемента с таким индексом не существует. Проверьте, что это число больше 0 и меньше размера коллекции");
        }
    }
}
