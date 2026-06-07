package ru.zelmex.salahovcourseach.controller.Dealers;  // ← пакет с маленькой

import ru.zelmex.salahovcourseach.model.Dealers;
import ru.zelmex.salahovcourseach.service.DealersService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditDealersDialog {
    @FXML private TextField nameField;
    @FXML private TextField regionField;
    @FXML private TextField phoneField;
    @FXML private TextField contactPersonField;
    @FXML private Label errorLabel;
    @FXML private Button okButton;

    private Stage dialogStage;
    private Dealers dealers;

    private void add() {
        try {
            Dealers newDealer = new Dealers();
            newDealer.setName(nameField.getText());
            newDealer.setRegion(regionField.getText());
            newDealer.setPhone(phoneField.getText());
            newDealer.setContactPerson(contactPersonField.getText());

            new DealersService().save(newDealer);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void edit() {
        try {
            dealers.setName(nameField.getText());
            dealers.setRegion(regionField.getText());
            dealers.setPhone(phoneField.getText());
            dealers.setContactPerson(contactPersonField.getText());

            new DealersService().update(dealers);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(event -> add());
    }

    public void setEditDialogStage(Stage dialogStage, Dealers dealers) {
        this.dealers = dealers;
        this.dialogStage = dialogStage;
        nameField.setText(dealers.getName());
        regionField.setText(dealers.getRegion());
        phoneField.setText(dealers.getPhone());
        contactPersonField.setText(dealers.getContactPerson());
        okButton.setOnAction(event -> edit());
    }
}