import java.util.Scanner;

/**
 * Класс, отвечающий за работу консоли, предоставляющей пользователю доступ к коллекции посредством команд
 */

public class Console {

    private final Scanner scanner;

    /**
     * Конструктор консоли
     *
     */
    public Console() {
        scanner = new Scanner(System.in);
    }


    public String getLine() {
        return scanner.nextLine();
    }


}
