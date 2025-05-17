package web.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import web.tables.Result;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public abstract class BaseBeanTest {
    protected static final List<Double> VALID_X_VALUES = Arrays.asList(
        -4.0, -3.5, -3.0, -2.5, -2.0, -1.5, -1.0, -0.5, 0.0,
        0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0
    );
    
    protected static final List<Double> VALID_R_VALUES = Arrays.asList(
        1.0, 1.5, 2.0, 2.5, 3.0
    );
    
    protected Result validResult;
    protected Result invalidResult;
    
    @BeforeEach
    void setUp() {
        validResult = createValidResult();
        invalidResult = createInvalidResult();
    }
    
    protected Result createValidResult() {
        Result result = new Result();
        result.setX(1.0);
        result.setR(2.0);
        result.setY(0.0);
        result.setHit(true);
        return result;
    }
    
    protected Result createInvalidResult() {
        Result result = new Result();
        result.setX(1.7);
        result.setR(2.7);
        result.setY(0.0);
        result.setHit(false);
        return result;
    }
    
    protected Result createResultWithCustomValues(double x, double y, double r) {
        Result result = new Result();
        result.setX(x);
        result.setY(y);
        result.setR(r);
        result.setHit(false);
        return result;
    }
    
    protected List<Result> createListOfValidResults(int count) {
        return Arrays.asList(new Result[count]).stream()
            .map(r -> createValidResult())
            .toList();
    }
    
    protected boolean isPointInArea(double x, double y, double r) {
        if (x >= 0 && y >= 0) {
            return (x * x + y * y) <= (r * r);
        }
        if (x <= 0 && y >= 0) {
            return (x >= -r/2) && (y <= r);
        }
        if (x <= 0 && y <= 0) {
            return (x >= -r) && (y >= -r) && (y >= -x - r);
        }
        return false;
    }
    
    protected void assertResultEquals(Result expected, Result actual) {
        assertEquals(expected.getX(), actual.getX(), "X coordinate should match");
        assertEquals(expected.getY(), actual.getY(), "Y coordinate should match");
        assertEquals(expected.getR(), actual.getR(), "R value should match");
        assertEquals(expected.isHit(), actual.isHit(), "Hit status should match");
    }
} 