package utils;

import exceptions.UnknownCommandException;
import server.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, запускающий команды, введенные пользователем
 */

public class CommandExecutor {

    /**
     * Map, содержащий все команды, доступные пользователю
     */
    private final Map<String, BaseCommand> commands;

    public CommandExecutor(CollectionManager manager) {
        commands = new HashMap<>() {
            {
                put("info", new InfoCommand("info", "вывести информацию о коллекции", manager));
                put("show", new ShowCommand("show", "вывести все элементы коллекции", manager));
                put("add", new AddCommand("add {element}", "добавить новый элемент в коллекцию", manager));
                put("update", new UpdateByIdCommand("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", manager));
                put("remove_by_id", new RemoveByIdCommand("remove_by_id id", "удалить элемент из коллекции по его id", manager));
                put("clear", new ClearCommand("clear", "очистить коллекцию", manager));
                put("remove_at", new RemoveAtCommand("remove_at index", "удалить элемент с заданным номером", manager));
                put("reorder", new ReorderCommand("reorder", "отсортировать коллекцию в порядке возрастания", manager));
                put("save", new SaveCommand("save", "сохранить коллекцию в файл", manager));
                put("sort", new SortCommand("sort", "отсортировать коллекцию в порядке убывания", manager));
                put("count_greater_than_distance", new CountGreaterThanDistanceCommand("count_greater_than_distance distance", "вывести количество элементов, значение поля distance которых больше заданного", manager));
                put("print_ascending", new PrintAscendingCommand("print_ascending", "вывести элементы коллекции в естественном порядке возрастания", manager));
                put("print_field_descending_distance", new PrintFieldDescendingDistanceCommand("print_field_descending_distance", "вывести значения поля distance в порядке убывания", manager));
                put("exit", new ExitCommand("exit", "Выйти из программы (без сохранения коллекции в файл)", manager));
            }
        };
        ExecuteScriptCommand executeScript = new ExecuteScriptCommand("execute_script", "считать и исполнить скрипт из указанного файла", manager, this);
        commands.put("execute_script", executeScript);
        HelpCommand help = new HelpCommand("help", "вывести справку по доступным командам", manager);
        help.setCommands(new ArrayList<BaseCommand>(commands.values()));
        commands.put("help", help);
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
