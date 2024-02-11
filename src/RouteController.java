import commands.*;
import routeClasses.Route;

import java.util.Stack;

/**
 * Класс, хранящий в себе коллекцию объектов класса Route и все команды для взаимодействия с ним.
 */
public class RouteController {

    /**
     * The Help command.
     */
    final HelpCommand helpCommand = new HelpCommand();
    /**
     * The Info command.
     */
    final InfoCommand infoCommand = new InfoCommand();
    /**
     * The Show command.
     */
    final ShowCommand showCommand = new ShowCommand();
    /**
     * The Add command.
     */
    final AddCommand addCommand = new AddCommand();
    /**
     * The Update by id command.
     */
    final UpdateByIdCommand updateByIdCommand = new UpdateByIdCommand();
    /**
     * The Remove by id command.
     */
    final RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
    /**
     * The Clear command.
     */
    final ClearCommand clearCommand = new ClearCommand();
    /**
     * The Remove at command.
     */
    final RemoveAtCommand removeAtCommand = new RemoveAtCommand();
    /**
     * The Reorder command.
     */
    final ReorderCommand reorderCommand = new ReorderCommand();
    /**
     * The Save command.
     */
    final SaveCommand saveCommand = new SaveCommand();
    /**
     * The Sort command.
     */
    final SortCommand sortCommand = new SortCommand();
    /**
     * The Count greater than distance command.
     */
    final CountGreaterThanDistanceCommand countGreaterThanDistanceCommand = new CountGreaterThanDistanceCommand();
    /**
     * The Print ascending command.
     */
    final PrintAscendingCommand printAscendingCommand = new PrintAscendingCommand();
    /**
     * The Print descending distance command.
     */
    final PrintFieldDescendingDistanceCommand printDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand();

    /**
     * Коллекция объектов класса Route
     */

    private Stack<Route> routes = new Stack<>();

    /**
     * Sets routes.
     *
     * @param val the val
     */
    void setRoutes(Stack<Route> val) {
        routes = val;
    }

    /**
     * Gets routes.
     *
     * @return the routes
     */
    Stack<Route> getRoutes() {
        return routes;
    }
}
