package commands;

import routeClasses.Route;

import java.util.Stack;

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
    protected Stack<Route> collection;

    /**
     * The Needs parse.
     */
    protected final boolean needsParse;


    /**
     * Конструктор класса.
     *
     * @param name        название команды
     * @param description описание команды
     * @param collection  коллекция, над которой производится действие
     * @param needsParse  флаг, указывающий, нужно ли парсить аргументы команды
     */
    public BaseCommand(String name, String description, Stack<Route> collection, boolean needsParse) {
        this.name = name;
        this.description = description;
        this.collection = collection;
        this.needsParse = needsParse;
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
    public void execute(String[] commandParts) {

    }

    /**
     * Метод, в котором выполняется логика команды, при этом нужно обработать передаваемые значения.
     *
     * @param commandParts массив, содержащий название и аргументы команды
     * @param parse флаг, указывающий, нужно ли парсить аргументы команды
     */
    public void execute(String[] commandParts, boolean parse) {

    }
}
