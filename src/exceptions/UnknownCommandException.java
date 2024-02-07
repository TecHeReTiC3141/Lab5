package exceptions;

public class UnknownCommandException extends Exception {

    public UnknownCommandException(String command) {
        super("Неизвестная команда: %s. Напиши help для получения списка доступных команд".formatted(command));
    }
}
