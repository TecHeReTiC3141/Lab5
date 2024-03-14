package server.commands;

import utils.CollectionManager;

/**
 * Класс, реализующий команду info, выводящую информацию о коллекции (тип, дата инициализации, количество элементов).
 */

public class InfoCommand extends BaseCommand {


    public InfoCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды info
     *
     * @param commandParts название и аргументы команды
     */
    public void execute(String[] commandParts) {
        System.out.println("Тип коллекции: " + manager.getCollectionClassName());
        System.out.println("Дата инициализации: " + manager.getInitDate());
        System.out.println("Количество элементов: " + manager.getCollectionSize());
    }
}
