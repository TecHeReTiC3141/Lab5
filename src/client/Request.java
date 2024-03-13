package client;

import routeClasses.Route;

import java.io.Serializable;

public class Request implements Serializable {

    private final String command;

    private final String[] args;

    private final Route route;

    public Request(String command, String[] args, Route route) {
        this.command = command;
        this.args = args;
        this.route = route;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public Route getRoute() {
        return route;
    }
}
