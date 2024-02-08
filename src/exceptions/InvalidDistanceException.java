package exceptions;

public class InvalidDistanceException extends Exception {
    public InvalidDistanceException() {
        super("Дистанция должна быть положительным вещественным числом");
    }
}
