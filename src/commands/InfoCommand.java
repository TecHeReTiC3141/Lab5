package commands;

import routeClasses.Route;

import java.util.Date;
import java.util.Stack;

public class InfoCommand {

    public void mainMethod(Stack<Route> collection, Date initDate) {
        System.out.printf("Тип коллекции: %s%n", collection.getClass().getName());
        System.out.printf("Дата инициализации: %s%n", initDate);
        System.out.printf("Количество элементов: %s%n", collection.size());
    }
}
