package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateReleaseValidatorTests {

    @Test
    void releaseWithoutNameTest() {
        Release release = new Release();
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> CreateReleaseValidator.validate(release)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_NAME));
    }




}
