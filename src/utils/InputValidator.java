package utils;

import exceptions.InvalidDistanceException;
import exceptions.InvalidNameException;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentsException;

import java.util.Set;

/**
* Класс, содержащий методы для проверки валидности ввода пользователя.
 */

public class InputValidator {

    /**
     * Проверка на валидность введенной команды.
     *
     * @param command введенная команда
     * @param validCommands список всех доступных команд, кроме execute_script и exit
     * @throws UnknownCommandException исключение, если команда не найдена
     */
    public static void checkIsValidCommand(String command, Set<String> validCommands) throws UnknownCommandException {
        if (!command.equals("execute_script") && !command.equals("exit") && !validCommands.contains(command)) {
            throw new UnknownCommandException(command);
        }
    }

    /**
     * Проверка на отсутствие аргументов команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */
    public static void checkIfNoArguments(String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 1) {
            throw new WrongArgumentsException("Команда %s не принимает аргументы".formatted(commandParts[0]));
        }
    }

    /**
     * Проверка на наличие ровно 1 аргумента команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */
    public static void checkIfOneArgument(String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 2) {
            throw new WrongArgumentsException("Команда %s принимает ровно 1 аргумент".formatted(commandParts[0]));
        }
    }

    /**
     * Проверка на наличие ровно 2 аргументов команды.
     * @param commandParts массив, содержащий команду и аргументы
     * @throws WrongArgumentsException исключение, если количество аргументов неверное
     */

    public static void checkIfTwoArguments(String[] commandParts) throws WrongArgumentsException {
        if (commandParts.length != 3) {
            throw new WrongArgumentsException("Команда %s принимает ровно 2 аргумента".formatted(commandParts[0]));
        }
    }

    /**
     * Проверка валидности имени (не пустое).
     * @param name имя
     * @return валидное имя
     * @throws InvalidNameException исключение, если имя невалидное
     */
    public static String checkName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
        return name;
    }

    /**
     * Проверка валидности дистанции (больше 0).
     * @param distance дистанция
     * @return валидная дистанция
     * @throws InvalidDistanceException исключение, если дистанция невалидная
     */
    public static double checkDistance(String distance) throws InvalidDistanceException {
        double d = Double.parseDouble(distance);
        if (d <= 0d) {
            throw new InvalidDistanceException();
        }
        return d;
    }

    /**
     * Проверка на валидность ответа (yes или no).
     * @param answer ответ
     */
    public static void checkIfYesOrNo(String answer) {
        if (!answer.equals("yes") && !answer.equals("no")) {
            throw new IllegalArgumentException("Введите yes или no");
        }
    }
}
