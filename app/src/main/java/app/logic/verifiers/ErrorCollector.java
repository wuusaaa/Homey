package app.logic.verifiers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barakm on 19/08/2017
 */

class ErrorCollector {


    private List<String> errors = new ArrayList<>();

    List<String> getErrors() {
        return errors;
    }


    void addError(String error) {
        errors.add(error);
    }


    void clear() {
        errors.clear();
    }
}
