package commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.CommandExecutor;
import utils.InputValidator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
                Scanner scanner = new Scanner(reader);
                executor.setDepth(executor.getDepth() + 1);
                while (scanner.hasNextLine()) {
                    boolean finished = executor.processCommand(scanner.nextLine());
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
