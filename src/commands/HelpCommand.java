package commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.InputValidator;

import java.util.ArrayList;

/**
 * Класс, реализующий команду help, которая выводит справку по доступным командам.
 */
public class HelpCommand extends BaseCommand {

    /**
     * Список команд для справки
     */
    ArrayList<BaseCommand> commands = new ArrayList<>();

    public HelpCommand(String name, String description, CollectionManager manager) {
        super(name, description, manager, false);
    }

    public void setCommands(ArrayList<BaseCommand> commands) {
        this.commands = commands;
    }

    /**
     * Main method.
     */
    public void execute(String[] commandParts) {
        try {
            System.out.println("Список доступных команд:");
            InputValidator.checkIfNoArguments(commandParts);
            for (BaseCommand command : commands) {
                System.out.println(command.getName() + ": " + command.getDescription());
            }
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
