package utils;

import commands.HelpCommand;
import exceptions.UnknownCommandException;

import java.util.ArrayList;
import java.util.Arrays;

public class InputValidator {

    private final ArrayList<String> ValidCommands = new ArrayList<>(
            Arrays.asList(
                    "help",
                    "info",
                    "show",
                    "add",
                    "update",
                    "remove_by_id",
                    "clear",
                    "save",
                    "execute_script",
                    "exit",
                    "remove_at",
                    "reorder",
                    "sort",
                    "count_greater_than_distance",
                    "print_ascending",
                    "print_field_descending_distance"
            )
    );


    public void checkIsValidCommand(String command) throws UnknownCommandException {
        if (!ValidCommands.contains(command)) {
            throw new UnknownCommandException(command);
        }
    }

    public void checkIfOneArgument(String[] commandParts) {
        if (commandParts.length != 1) {
            throw new IllegalArgumentException("Команда %s не принимает аргументы".formatted(commandParts[0]));
        }
    }
}
