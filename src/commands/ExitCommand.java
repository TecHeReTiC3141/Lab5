package commands;

import exceptions.ExitException;
import routeClasses.Route;

import java.util.Stack;

public class ExitCommand extends BaseCommand {


    /**
     * Конструктор класса.
     *
     * @param name        название команды
     * @param description описание команды
     * @param collection  коллекция, над которой производится действие
     */
    public ExitCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    public void execute(String[] commandParts) {
        throw new ExitException();
    }


}
