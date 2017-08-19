package app.logic.verifiers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barakm on 19/08/2017
 */

class ErrorCollector {


    List<String> errors = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
    }


}
