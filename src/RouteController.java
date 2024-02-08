import commands.*;
import routeClasses.Route;

import java.util.Stack;

public class RouteController {

    final HelpCommand helpCommand = new HelpCommand();
    final InfoCommand infoCommand = new InfoCommand();
    final ShowCommand showCommand = new ShowCommand();
    final AddCommand addCommand = new AddCommand();
    final UpdateByIdCommand updateByIdCommand = new UpdateByIdCommand();
    final RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
    final ClearCommand clearCommand = new ClearCommand();
    final RemoveAtCommand removeAtCommand = new RemoveAtCommand();
    final ReorderCommand reorderCommand = new ReorderCommand();
    final SaveCommand saveCommand = new SaveCommand();
    final SortCommand sortCommand = new SortCommand();
    final CountGreaterThanDistanceCommand countGreaterThanDistanceCommand = new CountGreaterThanDistanceCommand();
    final PrintAscendingCommand printAscendingCommand = new PrintAscendingCommand();
    final PrintFieldDescendingDistanceCommand printDescendingCommand = new PrintFieldDescendingDistanceCommand();


    private Stack<Route> routes = new Stack<>();

    void setRoutes(Stack<Route> val) {
        routes = val;
    }

    Stack<Route> getRoutes() {
        return routes;
    }
}
