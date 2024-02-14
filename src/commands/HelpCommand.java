package commands;

import exceptions.WrongArgumentsException;
import routeClasses.Route;
import utils.InputValidator;

import java.util.Stack;

/**
 * Класс, реализующий команду help, которая выводит справку по доступным командам.
 */
public class HelpCommand extends BaseCommand {


    public HelpCommand(String name, String description, Stack<Route> collection) {
        super(name, description, collection, false);
    }

    /**
     * Main method.
     */
    public void execute(String[] commandParts) {
        try {
            InputValidator.checkIfNoArguments(commandParts);
            System.out.println("Список доступных команд:");
            System.out.println("help : вывести справку по доступным командам");
            System.out.println("info : вывести информацию о коллекции");
            System.out.println("show : вывести все элементы коллекции");
            System.out.println("add : добавить новый элемент в коллекцию");
            System.out.println("update : обновить значение элемента коллекции, id которого равен заданному");
            System.out.println("remove_by_id : удалить элемент из коллекции по его id");
            System.out.println("clear : очистить коллекцию");
            System.out.println("save : сохранить коллекцию в файл");
            System.out.println("execute_script : считать и исполнить скрипт из указанного файла");
            System.out.println("exit : завершить программу (без сохранения в файл)");
            System.out.println("remove_at : удалить элемент с заданным номером");
            System.out.println("reorder : отсортировать коллекцию в порядке возрастания");
            System.out.println("sort : отсортировать коллекцию в порядке убывания");
            System.out.println("count_greater_than_distance : вывести количество элементов, значение поля distance которых больше заданного");
            System.out.println("print_ascending : вывести значения поля distance в порядке возрастания");
            System.out.println("print_field_descending_distance : вывести значения поля distance в порядке убывания");
        } catch (WrongArgumentsException e) {
            System.err.println(e.getMessage());
        }

    }
}
