package commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.CommandExecutor;
import utils.FileConsole;
import utils.InputValidator;

import java.io.FileReader;
import java.io.IOException;

public class ExecuteScriptCommand extends BaseCommand {

    private final CommandExecutor executor;

    public ExecuteScriptCommand(String name, String description, CollectionManager manager, CommandExecutor executor) {
        super(name, description, manager, false);
        this.executor = executor;
    }

    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfOneArgument(commandParts);
            String filename = commandParts[1];
            try (FileReader reader = new FileReader(filename)) {
                FileConsole console = new FileConsole(reader);
                executor.setDepth(executor.getDepth() + 1);
                while (console.hasNextLine()) {
                    boolean finished = executor.processCommand(console.getLine());
                    if (!finished) {
                        System.err.println("Ошибка при выполнении скрипта, проверьте правильность команд " + filename);
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            }

        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        };
    }
}
