package utils;

import commands.BaseCommand;
import exceptions.UnknownCommandException;

import java.util.Map;

public class CommandExecutor {
    /**
     * Map, содержащий все команды, доступные пользователю
     */

    private final Map<String, BaseCommand> commands;

    public CommandExecutor(Map<String, BaseCommand> commands) {
        this.commands = commands;
    }

    /**
     * глубина рекурсии, используется для предотвращения бесконечной рекурсии при вызове execute_script
     */
    private int depth = 0;

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    /**
     * Метод, обрабатывающий команду, введенную пользователем
     *
     * @param line строка, содержащая команду
     * @return true, если команда была успешно обработана, иначе false
     * @see BaseCommand#execute(String[])
     */

    public boolean processCommand(String line) {
        if (depth > 1000) {
            System.out.println("Превышена максимальная глубина рекурсии, вероятно, из-за рекурсивного вызова execute_script, проверьте скрипт на вызов самого себя");
            depth = 0;
            return false;
        }

        String[] commandParts = line.trim().split(" ");

        if (commandParts.length == 0) {
            return true;
        }

        try {
            String commandName = commandParts[0].toLowerCase();
            InputValidator.checkIsValidCommand(commandName, commands.keySet());
            BaseCommand command = commands.get(commandName);
            if (command.getNeedsParse()) {
                command.execute(commandParts, depth > 0);
            } else {
                command.execute(commandParts);
            }

        } catch (UnknownCommandException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
