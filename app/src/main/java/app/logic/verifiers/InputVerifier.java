package app.logic.verifiers;

import java.util.function.Consumer;

/**
 * Created by barakm on 19/08/2017
 */

public class InputVerifier {
    private ErrorCollector errorCollector = new ErrorCollector();

    public boolean isNameFieldOk(String name) {
        if (name.isEmpty()) {
            errorCollector.addError("You must insert a name");
            return false;
        }

        return true;
    }

    public String getMessagesToPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        errorCollector.getErrors()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String error) {
                        stringBuilder.append(error).append(System.lineSeparator());
                    }
                });

        errorCollector.clear();
        return stringBuilder.toString().trim();
    }

    public boolean isEmailOk(String email) {
        String[] split;

        if ((split = email.split("@")).length != 2 || (split = split[1].split("\\.")).length != 2) {
            errorCollector.addError("Invalid Email");
            return false;
        }

        for (char c : split[0].toCharArray()) {
            if (Character.isDigit(c)) {
                errorCollector.addError("Invalid Email");
                return false;
            }
        }


        for (char c : split[1].toCharArray()) {
            if (Character.isDigit(c)) {
                errorCollector.addError("Invalid Email");
                return false;
            }
        }

        return true;
    }
}
