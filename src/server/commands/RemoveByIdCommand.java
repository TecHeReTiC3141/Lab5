package server.commands;

import routeClasses.Route;
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

    public String execute(String[] commandParts, Route route) {
        long id = Long.parseLong(commandParts[1]);
        return manager.removeById(id);
    }
}
