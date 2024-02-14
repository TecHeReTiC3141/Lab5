import commands.*;
import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, хранящий в себе коллекцию объектов класса Route и все команды для взаимодействия с ним.
 */
public class RouteController {

    /**
     * Коллекция объектов класса Route
     */

    private Stack<Route> routes = new Stack<>();

    /**
     * The Help command.
     */
    final HelpCommand helpCommand = new HelpCommand("help", "вывести справку по доступным командам", routes);
    /**
     * The Info command.
     */
    final InfoCommand infoCommand = new InfoCommand("info", "вывести информацию о коллекции", routes);
    /**
     * The Show command.
     */
    final ShowCommand showCommand = new ShowCommand("show", "вывести все элементы коллекции", routes);
    /**
     * The Add command.
     */
    final AddCommand addCommand = new AddCommand("add", "добавить новый элемент в коллекцию", routes);
    /**
     * The Update by id command.
     */
    final UpdateByIdCommand updateByIdCommand = new UpdateByIdCommand("update", "обновить значение элемента коллекции, id которого равен заданному", routes);
    /**
     * The Remove by id command.
     */
    final RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand("remove_by_id", "удалить элемент из коллекции по его id", routes);
    /**
     * The Clear command.
     */
    final ClearCommand clearCommand = new ClearCommand("clear", "очистить коллекцию", routes);
    /**
     * The Remove at command.
     */
    final RemoveAtCommand removeAtCommand = new RemoveAtCommand("remove_at", "удалить элемент с заданным номером", routes);
    /**
     * The Reorder command.
     */
    final ReorderCommand reorderCommand = new ReorderCommand("reorder", "отсортировать коллекцию в порядке возрастания", routes);
    /**
     * The Save command.
     */
    final SaveCommand saveCommand = new SaveCommand("save", "сохранить коллекцию в файл", routes);
    /**
     * The Sort command.
     */
    final SortCommand sortCommand = new SortCommand("sort", "отсортировать коллекцию в порядке убывания", routes);
    /**
     * The Count greater than distance command.
     */
    final CountGreaterThanDistanceCommand countGreaterThanDistanceCommand = new CountGreaterThanDistanceCommand("count_greater_than_distance", "вывести количество элементов, значение поля distance которых больше заданного", routes);
    /**
     * The Print ascending command.
     */
    final PrintAscendingCommand printAscendingCommand = new PrintAscendingCommand("print_ascending", "вывести значения поля distance в порядке возрастания", routes);
    /**
     * The Print descending distance command.
     */
    final PrintFieldDescendingDistanceCommand printDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand("print_field_descending_distance", "вывести значения поля distance в порядке убывания", routes);

    /**
     * Sets routes.
     *
     * @param val the val
     */
    public void setRoutes(Stack<Route> val) {
        routes = val;
    }

    /**
     * Gets routes.
     *
     * @return the routes
     */
    public Stack<Route> getRoutes() {
        return routes;
    }
}
