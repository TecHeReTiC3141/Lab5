package server.commands;

import exceptions.WrongArgumentsException;
import utils.CollectionManager;
import utils.CommandExecutor;
import utils.FileConsole;
import utils.InputValidator;

import java.io.FileReader;
import java.io.IOException;

/**
 * Класс, реализующий команду execute_script, считывающую и исполняющую скрипт из указанного файла
 */

public class ExecuteScriptCommand extends BaseCommand {

    /**
     * Объект, выполняющий команды, с помощью которого будет выполнены команды скрипта
     */
    private final CommandExecutor executor;

    public ExecuteScriptCommand(String name, String description, CollectionManager manager, CommandExecutor executor) {
        super(name, description, manager, false);
        this.executor = executor;
    }

    /**
     * Метод, реализующий логику команды execute_script
     *
     * @param commandParts название команды и ее аргументы (файл, из которого нужно считать и исполнить скрипт)
     */

    // TODO: refactor this method
    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfOneArgument(commandParts);
            String filename = commandParts[1];
            try (FileReader reader = new FileReader(filename)) {
                FileConsole console = new FileConsole(reader);
                executor.setDepth(executor.getDepth() + 1);
                while (console.hasNextLine()) {
                    console.getLine();
//                    boolean finished = executor.processCommand(console.getLine());
//                    if (!finished) {
//                        System.err.println("Ошибка при выполнении скрипта, проверьте правильность команд " + filename);
//                        break;
//                    }
                }
                executor.setDepth(executor.getDepth() - 1);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            }

        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }
        ;
    }
}
