package ru.zelmex.salahovcourseach.controller.Shipments;

import ru.zelmex.salahovcourseach.motorcycleCompany;
import ru.zelmex.salahovcourseach.model.Shipments;
import ru.zelmex.salahovcourseach.service.ShipmentsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ShipmentsController {
    private List<Shipments> shipments;
    private ObservableList<ShipmentsTableItem> shipmentsObservable;

    @FXML private TableView<ShipmentsTableItem> shipmentsTable;
    @FXML private TableColumn<ShipmentsTableItem, Integer> shipmentIdColumn;
    @FXML private TableColumn<ShipmentsTableItem, Integer> modelIdColumn;
    @FXML private TableColumn<ShipmentsTableItem, Integer> dealerIdColumn;
    @FXML private TableColumn<ShipmentsTableItem, Integer> quantityColumn;
    @FXML private TableColumn<ShipmentsTableItem, String> dateColumn;

    @FXML private Button btnModelLines;
    @FXML private Button btnDealers;
    @FXML private Button btnShipments;
    @FXML private Button offButton;

    private void openShipmentDialog(boolean isEdit, Shipments shipment) {
        try {
            FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-shipments-dialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(motorcycleCompany.primaryStage);
            dialogStage.setMinWidth(400);
            dialogStage.setScene(new Scene(loader.load()));
            AddEditShipmentsDialog controller = loader.getController();
            if (isEdit) {
                dialogStage.setTitle("Редактировать поставку");
                controller.setEditDialogStage(dialogStage, shipment);
            } else {
                dialogStage.setTitle("Добавить поставку");
                controller.setAddDialogStage(dialogStage);
            }
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            System.out.println("Ошибка открытия окна: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void addShipment(ActionEvent event) {
        openShipmentDialog(false, null);
    }

    @FXML
    void editShipment(ActionEvent event) {
        updateList();
    }

    @FXML
    void deleteShipment(ActionEvent event) {
        ShipmentsTableItem currentItem = shipmentsTable.getSelectionModel().getSelectedItem();
        int currentItemId = shipmentsTable.getSelectionModel().getSelectedIndex();
        if (currentItemId != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить поставку #" + currentItem.getShipmentId() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ShipmentsService().delete(currentItem.getShipment());
                shipmentsTable.getItems().remove(currentItemId);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для удаления");
            alert.showAndWait();
        }
    }

    @FXML
    void onClickMotorcycles(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.modelLines);
    }

    @FXML
    void onClickDealers(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.dealers);
    }

    @FXML
    void onClickShipments(ActionEvent event) {
        updateList();
    }

    @FXML
    void powerOff(ActionEvent event) {
        motorcycleCompany.primaryStage.close();
    }

    @FXML
    void updateShipments(ActionEvent event) {
        ShipmentsTableItem selectedItem = shipmentsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = shipmentsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1 && selectedItem != null) {
            openShipmentDialog(true, selectedItem.getShipment());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }

    public void updateList() {
        try {
            shipments = new ShipmentsService().findAll();
            shipmentsObservable.clear();
            if (shipments != null) {
                for (Shipments shipment : shipments) {
                    shipmentsObservable.add(new ShipmentsTableItem(shipment));
                }
            }
            shipmentsTable.refresh();
        } catch (Exception e) {
            System.err.println("Ошибка в updateList():");
            e.printStackTrace();
        }
    }

    public void initialize() {
        shipmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));
        modelIdColumn.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        dealerIdColumn.setCellValueFactory(new PropertyValueFactory<>("dealerId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        shipmentsObservable = FXCollections.observableArrayList();
        shipmentsTable.setItems(shipmentsObservable);
        updateList();
    }
}