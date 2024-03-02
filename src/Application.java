import exceptions.ExitException;
import routeClasses.Route;
import utils.CollectionManager;
import utils.CommandExecutor;
import utils.SystemInConsole;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Класс, содержащий все компоненты консольного приложение и метод run, который запускает консольное приложение.
 */
public class Application {


    /**
     * Консоль, с помощью которой осуществляется ввод команд
     */
    private final SystemInConsole console;

    /**
     * Обработчик команд
     */
    private final CommandExecutor executor;

    /**
     * Менеджер коллекции
     */
    private final CollectionManager manager;

    public Application() {
        this.console = new SystemInConsole();
        this.manager = new CollectionManager(new Stack<Route>());
        this.executor = new CommandExecutor(manager);
    }

    /**
     * Метод, запускающий цикл работы консольного приложения из следующих шагов:
     * 1. Загрузка начальной коллекции
     * 2. Вывод приветствия и предложение ввести help
     * 3. Чтение и обработка команды из консоли
     */
    public void run() {
        manager.loadInitialCollection();
        System.out.println("Приветствую вас в программе для работы с коллекцией Route! Введите help для получения списка команд");
        while (true) {
            try {
                String line = console.getLine();
                executor.processCommand(line);
                System.out.println("-----------------------------------\n");
            } catch (NoSuchElementException e) {
                System.err.println("Достигнут конец ввода, завершение работы программы...");
                System.exit(130);
            } catch (ExitException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

}
