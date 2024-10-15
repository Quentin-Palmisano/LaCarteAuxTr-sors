package org.carbon.Interpreter;

import org.carbon.exception.FileFormatException;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpreterTest {

    @Test
    public void should_not_thow_when_line_is_valid() {
        // Given
        List<String> lines = List.of("C - 3 - 4", "A - Lara - 1 - 0 - N - AADADAGGA");

        // When
        // Then
        assertDoesNotThrow(() -> Interpreter.checkLinesFormat(lines));
    }

    @Test
    public void should_throw_when_lines_are_null() {
        // Given
        List<String> lines = null;

        // When
        // Then
        assertThrows(FileFormatException.class, () -> Interpreter.checkLinesFormat(lines));
    }

    @Test
    public void should_throw_when_lines_are_empty() {
        // Given
        List<String> lines = List.of();

        // When
        // Then
        assertThrows(FileFormatException.class, () -> Interpreter.checkLinesFormat(lines));
    }

    @Test
    public void should_throw_when_lines_are_invalid() {
        // Given
        List<String> lines = List.of("D - 3 - 4", "A - Lara - 1 - 0 - N - AADADAGGA");

        // When
        // Then
        assertThrows(FileFormatException.class, () -> Interpreter.checkLinesFormat(lines));
    }


}