package web.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import web.tables.Result;

import java.util.Set;

@Named("validationBean")
@ApplicationScoped
public class ValidationBean {

    private static final Set<Double> VALID_R_VALUES = Set.of(1.0, 1.5, 2.0, 2.5, 3.0);
    private static final Set<Double> VALID_X_VALUES = Set.of(
            -4.0, -3.5, -3.0, -2.5, -2.0, -1.5, -1.0, -0.5, 0.0,
            0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0);

    public void validateInput(Result result) {
        StringBuilder errors = new StringBuilder();

        if (!VALID_X_VALUES.contains(result.getX())) {
            errors.append("Invalid value for X. Must be one of ").append(VALID_X_VALUES).append("<br>");
        }

        if (!VALID_R_VALUES.contains(result.getR())) {
            errors.append("Invalid value for R. Must be one of ").append(VALID_R_VALUES).append("<br>");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
}