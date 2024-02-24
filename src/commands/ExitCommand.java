package commands;

import exceptions.ExitException;
import utils.CollectionManager;

public class ExitCommand extends BaseCommand {


    /**
     * Конструктор класса.
     *
     * @param name        название команды
     * @param description описание команды
     * @param collection  коллекция, над которой производится действие
     */
    public ExitCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    public void execute(String[] commandParts) {
        throw new ExitException();
    }


}
