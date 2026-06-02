package ru.university.lab4.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.university.lab4.core.DataProcessor;

public class WindowApp extends Application {
    private final DataProcessor dataProcessor = new DataProcessor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);
        inputArea.setPromptText("target=0\n-1\n0\n1\n2\n-1\n-4");
        inputArea.setText("""
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
                """);

        Button processButton = new Button("Обработать");
        ListView<String> outputList = new ListView<>();

        processButton.setOnAction(event -> {
            String[] inputLines = inputArea.getText().split("\\R");
            outputList.setItems(FXCollections.observableArrayList(dataProcessor.processPipeline(inputLines)));
        });

        VBox root = new VBox(10,
                new Label("Входные данные. При необходимости укажите target=<значение> отдельной строкой."),
                inputArea,
                processButton,
                new Label("Результат обработки"),
                outputList
        );
        root.setPadding(new Insets(16));
        VBox.setVgrow(inputArea, Priority.ALWAYS);
        VBox.setVgrow(outputList, Priority.ALWAYS);

        Scene scene = new Scene(root, 720, 560);
        stage.setTitle("Лаба 4 - обработка данных");
        stage.setScene(scene);
        stage.show();

        processButton.fire();
    }
}
