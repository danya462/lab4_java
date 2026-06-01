package ru.university.lab4.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProcessor {
    public String[] processPipeline(String[] rawInput) {
        InputPayload payload = parseInput(rawInput);
        List<List<Integer>> combinations = processData(payload.numbers(), payload.target());

        List<String> lines = new ArrayList<>();
        lines.add("Target = " + payload.target());
        lines.add("Numbers = " + payload.numbers());

        if (combinations.isEmpty()) {
            lines.add("No combinations found.");
        } else {
            lines.add("Combinations:");
            for (List<Integer> combination : combinations) {
                lines.add(formatCombination(combination, payload.target()));
            }
        }

        if (!payload.warnings().isEmpty()) {
            lines.add("Warnings:");
            lines.addAll(payload.warnings());
        }

        return lines.toArray(String[]::new);
    }

    public List<List<Integer>> processData(List<Integer> input, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (input.size() < 3) {
            return result;
        }

        int[] sorted = input.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(sorted);

        for (int i = 0; i < sorted.length - 2; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = sorted.length - 1;
            while (left < right) {
                int sum = sorted[i] + sorted[left] + sorted[right];
                if (sum == target) {
                    result.add(List.of(sorted[i], sorted[left], sorted[right]));
                    left++;
                    right--;

                    while (left < right && sorted[left] == sorted[left - 1]) {
                        left++;
                    }
                    while (left < right && sorted[right] == sorted[right + 1]) {
                        right--;
                    }
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    private InputPayload parseInput(String[] rawInput) {
        List<Integer> numbers = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        int target = 0;

        for (String line : rawInput) {
            if (line == null) {
                continue;
            }

            String normalized = line.trim();
            if (normalized.isEmpty() || normalized.startsWith("#")) {
                continue;
            }

            String lowerCase = normalized.toLowerCase();
            if (lowerCase.startsWith("target=") || lowerCase.startsWith("target:")) {
                String rawTarget = normalized.substring(normalized.indexOf('=') >= 0
                        ? normalized.indexOf('=') + 1
                        : normalized.indexOf(':') + 1).trim();
                try {
                    target = Integer.parseInt(rawTarget);
                } catch (NumberFormatException exception) {
                    warnings.add("Invalid target value skipped: " + normalized);
                }
                continue;
            }

            String[] tokens = normalized.split("[,;\\s]+");
            for (String token : tokens) {
                if (token.isBlank()) {
                    continue;
                }
                try {
                    numbers.add(Integer.parseInt(token));
                } catch (NumberFormatException exception) {
                    warnings.add("Invalid number skipped: " + token);
                }
            }
        }

        return new InputPayload(numbers, target, warnings);
    }

    private String formatCombination(List<Integer> combination, int target) {
        return combination.get(0) + " + " + combination.get(1) + " + " + combination.get(2) + " = " + target;
    }

    private record InputPayload(List<Integer> numbers, int target, List<String> warnings) {
    }
}
