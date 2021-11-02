package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReleaseValidatorTests {

    @Test
    void releaseWithoutNameTest() {
        Release release = new Release();
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> ReleaseValidator.validate(release, true)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_NAME));
    }

    @Test
    void releaseWithoutDescriptionTest() {
        Release release = new Release();
        release.setReleaseName("Test release");
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> ReleaseValidator.validate(release, true)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_DESCRIPTION));
    }

    @Test
    void releaseWithoutStatusTest() {
        Release release = new Release();
        release.setReleaseName("Test release");
        release.setReleaseDescription("Test description");
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> ReleaseValidator.validate(release, true)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_STATUS));
    }

    @Test
    void releaseWithInvalidStatusTest() {
        Release release = new Release();
        release.setReleaseName("Test release");
        release.setReleaseDescription("Test description");
        release.setReleaseStatus("Test status");
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> ReleaseValidator.validate(release, true)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_RELEASE_STATUS));
    }

    @Test
    void releaseWithInvalidInitialStatusTest() {
        Release release = new Release();
        release.setReleaseName("Test release");
        release.setReleaseDescription("Test description");
        release.setReleaseStatus("Done");
        ParameterValidationException thrown = assertThrows(
                ParameterValidationException.class,
                () -> ReleaseValidator.validate(release, true)
        );

        assertTrue(thrown.getMessage().equals(GlobalConstants.INVALID_INITIAL_RELEASE_STATUS));
    }




}
