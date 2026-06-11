package ru.zelmex.salahovcourseach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class motorcycleCompany extends Application {
    public static Stage primaryStage;
    public static Scene modelLines;
    public static Scene dealers;
    public static Scene shipments;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Загружаем все сцены
        modelLines = createScene("ModelLines-view.fxml");
        dealers = createScene("Dealers-view.fxml");
        shipments = createScene("Shipments-view.fxml");

        // Применяем CSS ко всем сценам
        modelLines.getStylesheets().add("base-styles.css");
        dealers.getStylesheets().add("base-styles.css");
        shipments.getStylesheets().add("base-styles.css");

        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(675);
        primaryStage.setTitle("Мотоциклы - Система учета");
        primaryStage.setScene(modelLines);
        primaryStage.show();
    }

    private Scene createScene(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(motorcycleCompany.class.getResource(name));
        return new Scene(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}