package web.beans;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.tables.Result;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidationBean Tests")
class ValidationBeanTest extends BaseBeanTest {
    
    private final ValidationBean validationBean = new ValidationBean();
    
    @Nested
    @DisplayName("Valid Input Tests")
    class ValidInputTests {
        
        @Test
        @DisplayName("Should validate correct X and R values")
        void shouldValidateCorrectValues() {
            Result result = createValidResult();
            assertDoesNotThrow(() -> validationBean.validateInput(result));
        }
        
        @ParameterizedTest
        @ValueSource(doubles = {-4.0, -3.5, -3.0, -2.5, -2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0})
        @DisplayName("Should validate all valid X values")
        void shouldValidateAllValidXValues(double xValue) {
            Result result = createValidResult();
            result.setX(xValue);
            assertDoesNotThrow(() -> validationBean.validateInput(result));
        }
        
        @ParameterizedTest
        @ValueSource(doubles = {1.0, 1.5, 2.0, 2.5, 3.0})
        @DisplayName("Should validate all valid R values")
        void shouldValidateAllValidRValues(double rValue) {
            Result result = createValidResult();
            result.setR(rValue);
            assertDoesNotThrow(() -> validationBean.validateInput(result));
        }
    }
    
    @Nested
    @DisplayName("Invalid Input Tests")
    class InvalidInputTests {
        
        @Test
        @DisplayName("Should throw exception for invalid X value")
        void shouldThrowExceptionForInvalidX() {
            Result result = createValidResult();
            result.setX(1.7);
            
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationBean.validateInput(result)
            );
            
            assertTrue(exception.getMessage().contains("Invalid value for X"));
        }
        
        @Test
        @DisplayName("Should throw exception for invalid R value")
        void shouldThrowExceptionForInvalidR() {
            Result result = createValidResult();
            result.setR(2.7);
            
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationBean.validateInput(result)
            );
            
            assertTrue(exception.getMessage().contains("Invalid value for R"));
        }
        
        @Test
        @DisplayName("Should throw exception for both invalid X and R values")
        void shouldThrowExceptionForBothInvalid() {
            Result result = createInvalidResult();
            
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationBean.validateInput(result)
            );
            
            assertTrue(exception.getMessage().contains("Invalid value for X"));
            assertTrue(exception.getMessage().contains("Invalid value for R"));
        }
    }
} 