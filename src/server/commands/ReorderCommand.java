package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду reorder, которая изменяет порядок элементов коллекции на обратный.
 */

public class ReorderCommand extends BaseCommand {


    public ReorderCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, изменяющий порядок элементов коллекции на обратный.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */

    public String execute(String[] commandParts) {
        manager.reorder();
        return "Порядок элементов коллекции изменен на обратный";
    }
}
