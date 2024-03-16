package client.validators;

import client.Request;
import exceptions.WrongArgumentsException;

public class UpdateByIdValidator extends ReadValidator {
    @Override
    public Request validate(String command, String[] args, boolean parse) {
        try {
            checkIfOneArgument(command, args);
            Long id = Long.parseLong(args[0]);
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть целым числом");
            return null;
        }
    }
}
