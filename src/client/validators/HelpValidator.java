package client.validators;

import client.Request;
import exceptions.WrongArgumentsException;

public class HelpValidator extends BaseValidator {

    public Request validate(String command, String[] args) {
        try {
            checkIfOneArgument(args);
            return new Request(command, args, null);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
