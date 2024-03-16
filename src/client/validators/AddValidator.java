package client.validators;

import client.Request;
import exceptions.WrongArgumentsException;

public class AddValidator extends ReadValidator {

    @Override
    public Request validate(String command, String[] args, boolean parse) {
        try {
            checkIfNoArguments(command, args);
            return super.validate(command, args, parse);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
