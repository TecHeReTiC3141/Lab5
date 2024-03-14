package server.commands;

import exceptions.ExitException;
import utils.CollectionManager;

/**
 * Класс команды exit, завершающей программу.
 */

public class ExitCommand extends BaseCommand {


    /**
     * Конструктор класса.
     *
     * @param name        название команды
     * @param description описание команды
     * @param manager   менеджер коллекции
     */
    public ExitCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    /**
     * Метод, реализующий логику команды exit
     * @param commandParts название команды и ее аргументы
     */
    public String execute(String[] commandParts) {
        throw new ExitException();
    }

}
