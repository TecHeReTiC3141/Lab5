package commands;

import routeClasses.Route;

import java.util.Date;
import java.util.Stack;

public class InfoCommand {

    public void mainMethod(Stack<Route> collection, Date initDate) {
        System.out.println("Тип коллекции: " + collection.getClass().getName());
        System.out.println("Дата инициализации: " + initDate);
        System.out.println("Количество элементов: " + collection.size());
    }
}
