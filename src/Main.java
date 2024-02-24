/**
 * Класс, содержащий метод main, который запускает консольное приложение.
 */

public class Main {

    /**
     * Точка запуска консольного приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        // TODO: повысить модульность через введение InputManager (для ввода), CollectionManager (для манипуляций с коллекциями)

        Application application = new Application();
        application.run();
    }
}