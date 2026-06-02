package ru.university.lab4.console;

import ru.university.lab4.core.DataProcessor;
import ru.university.lab4.core.FileUtils;
import ru.university.lab4.models.InputArgs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public final class ConsoleApp {
    private ConsoleApp() {
    }

    public static void main(String[] args) {
        InputArgs inputArgs;
        try {
            inputArgs = InputArgs.parse(args);
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
            printUsage();
            return;
        }

        DataProcessor dataProcessor = new DataProcessor();
        try {
            String[] inputLines = FileUtils.readLines(inputArgs.inputPath());
            String[] outputLines = dataProcessor.processPipeline(inputLines);

            for (String line : outputLines) {
                System.out.println(line);
            }

            if (inputArgs.outputPath() != null) {
                FileUtils.writeLines(inputArgs.outputPath(), List.of(outputLines));
                System.out.println("Файл результата сохранен: " + inputArgs.outputPath().toAbsolutePath());
            }
        } catch (IOException exception) {
            System.err.println("Ошибка работы с файлом: " + exception.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Запуск: mvn exec:java -Dexec.args=\"-i test/input01.txt [-o output/result.txt]\"");
        System.out.println("Аргументы: " + Arrays.toString(new String[]{"-i", "inputFile", "-o", "outputFile"}));
    }
}
