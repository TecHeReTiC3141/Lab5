package commands;

public interface ICommand {

    String getName();
    String getDescription();

    void execute(String[] commandParts);
    void execute(String[] commandParts, boolean parse);
}
