package ru.zelmex.salahovcourseach.controller.Shipments;

import ru.zelmex.salahovcourseach.model.Shipments;
import ru.zelmex.salahovcourseach.service.ShipmentsService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddEditShipmentsDialog {

    @FXML
    private TextField modelIdField;

    @FXML
    private TextField dealerIdField;

    @FXML
    private TextField quantityField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button okButton;

    private Stage dialogStage;
    private Shipments shipment;

    void add() {
        try {
            if (modelIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"ID Модели\"");
            }
            if (dealerIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"ID Дилера\"");
            }
            if (quantityField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Количество\"");
            }
            if (dateField.getValue() == null) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Дата\"");
            }

            Shipments newShipment = new Shipments();
            newShipment.setModelId(Integer.parseInt(modelIdField.getText()));
            newShipment.setDealerId(Integer.parseInt(dealerIdField.getText()));
            newShipment.setQuantity(quantityField.getText());
            newShipment.setDate(dateField.getValue());

            new ShipmentsService().save(newShipment);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("ID и Количество должны быть числами!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dateField.setValue(LocalDate.now());
        okButton.setOnAction((www) -> add());
    }

    void edit() {
        try {
            if (modelIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"ID Модели\"");
            }
            if (dealerIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"ID Дилера\"");
            }
            if (quantityField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Количество\"");
            }
            if (dateField.getValue() == null) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Дата\"");
            }

            shipment.setModelId(Integer.parseInt(modelIdField.getText()));
            shipment.setDealerId(Integer.parseInt(dealerIdField.getText()));
            shipment.setQuantity(quantityField.getText());
            shipment.setDate(dateField.getValue());

            new ShipmentsService().update(shipment);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("ID и Количество должны быть числами!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setEditDialogStage(Stage dialogStage, Shipments shipment) {
        this.dialogStage = dialogStage;
        this.shipment = shipment;

        modelIdField.setText(String.valueOf(shipment.getModelId()));
        dealerIdField.setText(String.valueOf(shipment.getDealerId()));
        quantityField.setText(String.valueOf(shipment.getQuantity()));
        dateField.setValue(shipment.getDate());

        okButton.setOnAction((www) -> edit());
    }
}