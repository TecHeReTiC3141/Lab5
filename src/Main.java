import commands.*;
import routeClasses.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Класс, содержащий метод main, который запускает консольное приложение.
 */

public class Main {

    /**
     * Точка запуска консольного приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        Stack<Route> routes = new Stack<>();
        Map<String, BaseCommand> commands = new HashMap<>() {
            {
                put("info", new InfoCommand("info", "вывести информацию о коллекции", routes));
                put("show", new ShowCommand("show", "вывести все элементы коллекции", routes));
                put("add", new AddCommand("add", "добавить новый элемент в коллекцию", routes));
                put("update", new UpdateByIdCommand("update", "обновить значение элемента коллекции, id которого равен заданному", routes));
                put("remove_by_id", new RemoveByIdCommand("remove_by_id", "удалить элемент из коллекции по его id", routes));
                put("clear", new ClearCommand("clear", "очистить коллекцию", routes));
                put("remove_at", new RemoveAtCommand("remove_at", "удалить элемент с заданным номером", routes));
                put("reorder", new ReorderCommand("reorder", "отсортировать коллекцию в порядке возрастания", routes));
                put("save", new SaveCommand("save", "сохранить коллекцию в файл", routes));
                put("sort", new SortCommand("sort", "отсортировать коллекцию в порядке убывания", routes));
                put("count_greater_than_distance", new CountGreaterThanDistanceCommand("count_greater_than_distance", "вывести количество элементов, значение поля distance которых больше заданного", routes));
                put("print_ascending", new PrintAscendingCommand("print_ascending", "вывести значения поля distance в порядке возрастания", routes));
                put("print_field_descending_distance", new PrintFieldDescendingDistanceCommand("print_field_descending_distance", "вывести значения поля distance в порядке убывания", routes));

            }
        };
        HelpCommand help = new HelpCommand("help", "вывести справку по доступным командам", routes);
        help.setCommands(new ArrayList<BaseCommand>(commands.values()));
        commands.put("help", help);

        Console console = new Console(routes, commands);
        console.run();
    }
}