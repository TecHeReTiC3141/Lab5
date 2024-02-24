import exceptions.ExitException;
import utils.CollectionManager;
import utils.CommandExecutor;

import java.util.NoSuchElementException;


public class Application {

    private final Console console;
    private final CommandExecutor executor;
    private final CollectionManager manager;

    public Application(Console console, CommandExecutor executor, CollectionManager manager) {
        this.console = console;
        this.executor = executor;
        this.manager = manager;
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
                System.out.println("\n-----------------------------------");
            } catch (NoSuchElementException e) {
                System.err.println("Достигнут конец ввода, завершение работы программы...");
                System.exit(130);
            } catch (ExitException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
