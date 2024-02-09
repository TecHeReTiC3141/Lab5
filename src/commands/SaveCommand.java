package commands;

import routeClasses.Route;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class SaveCommand {

    public void mainMethod(String filename, Stack<Route> collection) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста, нечего сохранять.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Route route : collection) {
                writer.write(route.getSerializedString() + '\n');
            }
            System.out.println("Коллекция успешно сохранена в файл " + filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
