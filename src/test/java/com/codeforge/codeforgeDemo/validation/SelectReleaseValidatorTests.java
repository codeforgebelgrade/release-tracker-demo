package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectReleaseValidatorTests {

    @Test
    void searchReleasesWithInvalidStatusTest() {
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> SelectReleaseValidator.validate("Invalid status", null, null)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_STATUS));
    }

    @Test
    void searchReleasesWithInvalidReleaseDateTest() {
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> SelectReleaseValidator.validate("Created", "2021-48-48", null)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_DATETIME_VALUE));
    }

    @Test
    void searchReleasesWithNegativePageSizeTest() {
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> SelectReleaseValidator.validate("Created", "2021-11-11", -1)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_ROWS_VALUE));
    }
}
