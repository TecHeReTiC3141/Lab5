import commands.HelpCommand;
import commands.InfoCommand;
import routeClasses.Route;

import java.util.Stack;

public class RouteController {

    final HelpCommand helpCommand = new HelpCommand();
    final InfoCommand infoCommand = new InfoCommand();

    private Stack<Route> routes = new Stack<>();

    void setRoutes(Stack<Route> val) {
        routes = val;
    }

    Stack<Route> getRoutes() {
        return routes;
    }
}
