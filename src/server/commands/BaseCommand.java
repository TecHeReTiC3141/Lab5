package server.commands;

import routeClasses.Route;
import utils.CollectionManager;

/**
 * Абстрактный класс, реализующий интерфейс ICommand.
 */
public abstract class BaseCommand implements ICommand {

    /**
     * The Name.
     */
    protected final String name;
    /**
     * The Description.
     */
    protected final String description;

    /**
     * The Collection.
     */
    protected CollectionManager manager;

    /**
     * The Needs parse.
     */
    protected final boolean needsParse;


    /**
     * Конструктор класса.
     *
     * @param name        название команды
     * @param description описание команды
     * @param manager   менеджер коллекции
     * @param needsParse  флаг, указывающий, нужно ли парсить аргументы команды
     */
    public BaseCommand(String name, String description, CollectionManager manager, boolean needsParse) {
        this.name = name;
        this.description = description;
        this.needsParse = needsParse;
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets needs parse.
     *
     * @return the needs parse
     */
    public boolean getNeedsParse() {
        return needsParse;
    }

    /**
     * Метод, в котором выполняется логика команды.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     */
    public String execute(String[] commandParts, Route route) {
        return "";
    }

    /**
     * Метод, в котором выполняется логика команды, при этом нужно обработать передаваемые значения.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     * @param parse флаг, указывающий, нужно ли парсить аргументы команды
     */
    public String execute(String[] commandParts, Route route, boolean parse) {
        return "";
    }
}
