package server.commands;

import utils.CollectionManager;

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
        long id = Long.parseLong(commandParts[1]);
        manager.removeById(id);
    }
}
