# Lab 4 - Data Processing Pipeline

Course lab for building a reusable processing pipeline with one shared core and two client applications.

Variant 15:
Find all unique combinations of 3 integers whose sum is equal to a target value.

Example:

`[-1, 0, 1, 2, -1, -4], target=0 -> [-1, -1, 2], [-1, 0, 1]`

## Project structure

- `src/main/java/ru/university/lab4/core` - reusable business logic
- `src/main/java/ru/university/lab4/console` - console client
- `src/main/java/ru/university/lab4/gui` - JavaFX client
- `src/main/java/ru/university/lab4/models` - command-line model
- `test` - sample input files
- `output` - result folder for console mode

## Input format

The pipeline accepts an array of strings.

- Empty lines are ignored.
- A line like `target=0` or `target: 5` sets the target sum.
- The remaining lines may contain one integer or several integers separated by spaces, commas, or semicolons.

If target is not provided, `0` is used.

## Console run

```powershell
mvn compile
mvn exec:java -Dexec.args="-i test/input01.txt -o output/result01.txt"
```

## JavaFX run

```powershell
mvn javafx:run
```

## Sample input

```text
target=0
-1
0
1
2
-1
-4
3
-2
4
-3
```
