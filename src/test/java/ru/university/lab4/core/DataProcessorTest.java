package ru.university.lab4.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DataProcessorTest {
    private final DataProcessor dataProcessor = new DataProcessor();

    @Test
    void shouldFindVariant15Triples() {
        String[] output = dataProcessor.processPipeline(new String[]{
                "target=0",
                "-1",
                "0",
                "1",
                "2",
                "-1",
                "-4"
        });

        String joined = String.join("\n", output);
        assertTrue(joined.contains("-1 + -1 + 2 = 0"));
        assertTrue(joined.contains("-1 + 0 + 1 = 0"));
    }

    @Test
    void shouldReportInvalidTokens() {
        String[] output = dataProcessor.processPipeline(new String[]{
                "target=3",
                "1",
                "abc",
                "2",
                "3"
        });

        String joined = String.join("\n", output);
        assertTrue(joined.contains("Некорректное число пропущено: abc"));
        assertTrue(joined.contains("Подходящие тройки не найдены.") || joined.contains("1 + 2 + 3 = 6") == false);
    }
}
