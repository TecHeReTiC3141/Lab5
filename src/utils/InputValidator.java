package utils;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentsException;

import java.util.ArrayList;
import java.util.Arrays;

public class InputValidator {

    private static final ArrayList<String> ValidCommands = new ArrayList<>(
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


    public static void checkIsValidCommand(String command) throws UnknownCommandException {
        if (!ValidCommands.contains(command)) {
            throw new UnknownCommandException(command);
        }
    }

    public static void checkIfNoArguments(String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 1) {
            throw new WrongArgumentsException("Команда %s не принимает аргументы".formatted(commandParts[0]));
        }
    }

    public static void checkIfOneArgument(String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 2) {
            throw new WrongArgumentsException("Команда %s принимает ровно 1 аргумент".formatted(commandParts[0]));
        }
    }

    public static void checkName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
    }

    public static double checkDistance(String distance) throws InvalidDistanceException {
=        double d = Double.parseDouble(distance);
        if (d <= 0d) {
            throw new InvalidDistanceException();
        }
        return d;
    }
}
