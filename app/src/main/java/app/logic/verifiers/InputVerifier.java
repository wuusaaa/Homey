package app.logic.verifiers;

/**
 * Created by barakm on 19/08/2017
 */

public class InputVerifier {
    private ErrorCollector errorCollector = new ErrorCollector();

    public boolean isNameFieldOk(String name) {
        if (name.isEmpty()) {
            errorCollector.addError("You must fill task's name");
            return false;
        }

        return true;
    }

    public String getMessagesToPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        errorCollector.getErrors()
                .forEach(error -> stringBuilder.append(error).append("\n"));

        return stringBuilder.toString();
    }
}
