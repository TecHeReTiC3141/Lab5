package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс, реализующий команду help, которая выводит справку по доступным командам.
 */
public class HelpCommand extends BaseCommand {

    ArrayList<BaseCommand> commands = new ArrayList<>();


    public HelpCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    public void setCommands(ArrayList<BaseCommand> commands) {
        this.commands = commands;
    }

    /**
     * Main method.
     */
    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            for (BaseCommand command : commands) {
                System.out.println(command.getName() + " : " + command.getDescription());
            }
            System.out.println("execute_script : считать и исполнить скрипт из указанного файла");
            System.out.println("exit : завершить программу (без сохранения в файл)");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
