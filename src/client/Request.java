package client;

import routeClasses.Route;

public class Request {

    private String command;

    private String[] args;

    private Route route;

    public Request(String command, String[] args, Route route) {
        this.command = command;
        this.args = args;
        this.route = route;
    }
}
