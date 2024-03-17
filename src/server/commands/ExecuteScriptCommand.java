package server.commands;

import utils.CollectionManager;
import utils.CommandExecutor;
import utils.FileConsole;

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
    public String execute(String[] commandParts) {
        System.out.println("in execute_script");
        String filename = commandParts[0];
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
        return "Execute_script";
    }
}
