package client.validators;

import client.Request;
import exceptions.WrongArgumentsException;

public class OneIntArgValidator extends BaseValidator {

    public Request validate(String command, String[] args) {
        try {
            checkIfOneArgument(command, args);
            int firstArg = Integer.parseInt(args[1]);
            return super.validate(command, args);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
