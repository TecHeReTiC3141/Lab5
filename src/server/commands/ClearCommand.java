package server.commands;

import routeClasses.Route;
import utils.CollectionManager;

/**
 * Класс, реализующий команду clear, очищающую коллекцию.
 */

public class ClearCommand extends BaseCommand {

    public ClearCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, очищающий коллекцию.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public String execute(String[] commandParts, Route route) {
        manager.clearCollection();
        return "Коллекция очищена";
    }
}
