package ru.zelmex.salahovcourseach.controller.Shipments;

import ru.zelmex.salahovcourseach.model.ModelLines;
import ru.zelmex.salahovcourseach.model.Dealers;
import ru.zelmex.salahovcourseach.model.Shipments;
import ru.zelmex.salahovcourseach.service.ModelLinesService;
import ru.zelmex.salahovcourseach.service.DealersService;
import ru.zelmex.salahovcourseach.service.ShipmentsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddEditShipmentsDialog {

    @FXML private ComboBox<ModelLines> modelComboBox;
    @FXML private ComboBox<Dealers> dealerComboBox;
    @FXML private TextField quantityField;
    @FXML private DatePicker dateField;
    @FXML private Label errorLabel;
    @FXML private Button okButton;

    private Stage dialogStage;
    private Shipments shipment;

    void add() {
        try {
            ModelLines selectedModel = modelComboBox.getValue();
            Dealers selectedDealer = dealerComboBox.getValue();

            if (selectedModel == null) {
                throw new IllegalArgumentException("Нужно выбрать модель!");
            }
            if (selectedDealer == null) {
                throw new IllegalArgumentException("Нужно выбрать дилера!");
            }
            if (quantityField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Количество\"");
            }
            if (dateField.getValue() == null) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Дата\"");
            }

            Shipments newShipment = new Shipments();
            newShipment.setModelId(selectedModel.getModelId());
            newShipment.setDealerId(selectedDealer.getDealerId());
            newShipment.setQuantity(quantityField.getText());
            newShipment.setDate(dateField.getValue());

            new ShipmentsService().save(newShipment);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("Количество должно быть числом!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Ошибка: " + e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        // Загружаем списки моделей и дилеров
        modelComboBox.setItems(FXCollections.observableArrayList(new ModelLinesService().findAll()));
        dealerComboBox.setItems(FXCollections.observableArrayList(new DealersService().findAll()));

        dateField.setValue(LocalDate.now());
        okButton.setOnAction((www) -> add());
    }

    void edit() {
        try {
            ModelLines selectedModel = modelComboBox.getValue();
            Dealers selectedDealer = dealerComboBox.getValue();

            if (selectedModel == null) {
                throw new IllegalArgumentException("Нужно выбрать модель!");
            }
            if (selectedDealer == null) {
                throw new IllegalArgumentException("Нужно выбрать дилера!");
            }
            if (quantityField.getText().isEmpty()) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Количество\"");
            }
            if (dateField.getValue() == null) {
                throw new IllegalArgumentException("Нужно заполнить поле \"Дата\"");
            }

            shipment.setModelId(selectedModel.getModelId());
            shipment.setDealerId(selectedDealer.getDealerId());
            shipment.setQuantity(quantityField.getText());
            shipment.setDate(dateField.getValue());

            new ShipmentsService().update(shipment);
            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("Количество должно быть числом!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Ошибка: " + e.getMessage());
        }
    }

    public void setEditDialogStage(Stage dialogStage, Shipments shipment) {
        this.dialogStage = dialogStage;
        this.shipment = shipment;

        // Загружаем списки моделей и дилеров
        modelComboBox.setItems(FXCollections.observableArrayList(new ModelLinesService().findAll()));
        dealerComboBox.setItems(FXCollections.observableArrayList(new DealersService().findAll()));

        // Выбираем нужную модель в ComboBox
        for (ModelLines model : modelComboBox.getItems()) {
            if (model.getModelId() == shipment.getModelId()) {
                modelComboBox.setValue(model);
                break;
            }
        }

        // Выбираем нужного дилера в ComboBox
        for (Dealers dealer : dealerComboBox.getItems()) {
            if (dealer.getDealerId() == shipment.getDealerId()) {
                dealerComboBox.setValue(dealer);
                break;
            }
        }

        quantityField.setText(String.valueOf(shipment.getQuantity()));
        dateField.setValue(shipment.getDate());

        okButton.setOnAction((www) -> edit());
    }
}