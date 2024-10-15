package org.carbon.utils;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    @Test
    public void should_return_string_without_whitespace_given_not_null_string() {
        // Given
        String str = " Hello Carbon  IT      !";
        String expectedString = "HelloCarbonIT!";

        // When
        String result = StringUtils.removeWhitespace(str);

        // Then
        assertEquals(expectedString, result);
    }

    @Test
    public void should_return_null_given_null_string() {
        // Given
        String str = null;
        String expectedString = null;

        // When
        String result = StringUtils.removeWhitespace(str);

        // Then
        assertEquals(expectedString, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "# This is a comment",
        "A - Lara - 1 - 2 - S - AADADAGGA",
        "C - 3 - 4",
        "M - 1 - 2",
        "T - 1 - 2 - 3"
    })
    public void should_return_true_given_accepted_line(String line) {
        assertTrue(StringUtils.isAcceptedLine(line));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "It is not a comment",
        "B - Lara - 1 - 2 - S - AADADAGGA",
        "D - 3 - 4",
        "N - 1 - 2",
        "U - 1 - 2 - 3"
    })
    public void should_return_false_given_not_accepted_line(String line) {
        assertFalse(StringUtils.isAcceptedLine(line));
    }
}