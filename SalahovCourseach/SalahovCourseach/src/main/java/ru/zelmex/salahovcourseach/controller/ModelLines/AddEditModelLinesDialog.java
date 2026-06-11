package ru.zelmex.salahovcourseach.controller.ModelLines;

import ru.zelmex.salahovcourseach.model.ModelLines;
import ru.zelmex.salahovcourseach.service.ModelLinesService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEditModelLinesDialog implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextArea specificationsField;
    @FXML
    private TextField priceField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button cancelButton;

    @FXML
    private void cancel() {
        dialogStage.close();
    }
    @FXML
    private Button okButton;

    private Stage dialogStage;
    private ModelLines modelLines;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnAction(event -> cancel());
    }
    @FXML
    private void add() {
        try {
            ModelLines newModelLines = new ModelLines();  // <-- объявляем ЗДЕСЬ
            newModelLines.setName(nameField.getText());
            newModelLines.setType(typeField.getText());
            newModelLines.setSpecifications(specificationsField.getText());

            BigDecimal price = new BigDecimal(priceField.getText());
            newModelLines.setPrice(price);

            new ModelLinesService().save(newModelLines);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("Неверный формат цены!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }
    @FXML
    void edit() {
        try {
            modelLines.setName(nameField.getText());
            modelLines.setType(typeField.getText());
            modelLines.setSpecifications(specificationsField.getText());

            BigDecimal price = new BigDecimal(priceField.getText());
            modelLines.setPrice(price);

            new ModelLinesService().update(modelLines);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("Неверный формат цены!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(event -> add());
    }

    public void setEditDialogStage(Stage dialogStage, ModelLines modelLines) {
        this.modelLines = modelLines;
        this.dialogStage = dialogStage;

        nameField.setText(modelLines.getName());
        typeField.setText(modelLines.getType());
        specificationsField.setText(modelLines.getSpecifications());
        if (modelLines.getPrice() != null) {
            priceField.setText(modelLines.getPrice().toString());
        }

        okButton.setOnAction(event -> edit());
    }
}