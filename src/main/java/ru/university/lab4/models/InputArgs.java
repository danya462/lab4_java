package ru.university.lab4.models;

import java.nio.file.Path;

public record InputArgs(Path inputPath, Path outputPath) {
    public static InputArgs parse(String[] args) {
        Path inputPath = null;
        Path outputPath = null;

        for (int index = 0; index < args.length; index++) {
            String argument = args[index];
            if ("-i".equals(argument)) {
                inputPath = requireValue(args, ++index, "-i");
            } else if ("-o".equals(argument)) {
                outputPath = requireValue(args, ++index, "-o");
            } else {
                throw new IllegalArgumentException("Неизвестный аргумент: " + argument);
            }
        }

        if (inputPath == null) {
            throw new IllegalArgumentException("Нужно указать входной файл.");
        }

        return new InputArgs(inputPath, outputPath);
    }

    private static Path requireValue(String[] args, int index, String option) {
        if (index >= args.length) {
            throw new IllegalArgumentException("Не указано значение для опции " + option);
        }
        return Path.of(args[index]);
    }
}
