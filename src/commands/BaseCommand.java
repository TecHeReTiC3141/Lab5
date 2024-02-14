package commands;

import routeClasses.Route;

import java.util.Stack;

public abstract class BaseCommand implements ICommand {

    protected final String name;
    protected final String description;

    protected Stack<Route> collection;

    protected final boolean needsParse;


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

    public boolean getNeedsParse() {
        return needsParse;
    }

    public void execute(String[] commandParts) {

    }
    public void execute(String[] commandParts, boolean parse) {

    }
}
