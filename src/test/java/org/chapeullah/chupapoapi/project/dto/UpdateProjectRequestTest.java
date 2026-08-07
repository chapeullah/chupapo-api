package org.chapeullah.chupapoapi.project.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateProjectRequestTest  {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;


    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }

    @Test
    void shouldRejectRequestWhenAllFieldsAreNull() {
        UpdateProjectRequest request = new UpdateProjectRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        Set<ConstraintViolation<UpdateProjectRequest>> violations = validator.validate(request);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("At least one project field must be provided");
    }

    @Test
    void shouldAcceptRequestWhenAtLeastOneFieldIsProvided() {
        UpdateProjectRequest request = new UpdateProjectRequest(
                null,
                "Author Name",
                null,
                null,
                null,
                null,
                null);
        Set<ConstraintViolation<UpdateProjectRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest(name = "[{index}] invalid slug: \"{0}\"")
    @ValueSource(strings = {
            "Some",
            "some-Slug",
            "some_slug",
            "-some-slug",
            "some-slug-",
            "some--slug",
            "some slug",
            "some123",
            "-",
            "_",
            ""
    })
    void shouldRejectInvalidSlug(String slug) {
        UpdateProjectRequest request = requestWithSlug(slug);
        Set<ConstraintViolation<UpdateProjectRequest>> violations =
                validator.validate(request);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(
                        "Slug must contain only lowercase English letters separated by single hyphens");
    }

    @ParameterizedTest(name = "[{index}] invalid slug: \"{0}\"")
    @ValueSource(strings = {
            "project",
            "some-project",
            "some-long-project"
    })
    void shouldAcceptValidSlug(String slug) {
        UpdateProjectRequest request = requestWithSlug(slug);
        Set<ConstraintViolation<UpdateProjectRequest>> violations =
                validator.validate(request);
        assertThat(violations).isEmpty();
    }

    private static UpdateProjectRequest requestWithSlug(String slug) {
        return new UpdateProjectRequest(
                slug,
                null,
                null,
                null,
                null,
                null,
                null);
    }

}
