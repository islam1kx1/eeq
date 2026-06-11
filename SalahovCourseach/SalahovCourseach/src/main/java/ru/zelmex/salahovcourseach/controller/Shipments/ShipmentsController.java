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
    private ObservableList<ShipmentsTableItem> shipmentsObservable;

    @FXML private TableView<ShipmentsTableItem> shipmentsTable;
    @FXML private TableColumn<ShipmentsTableItem, String> modelColumn;
    @FXML private TableColumn<ShipmentsTableItem, String> dealerColumn;
    @FXML private TableColumn<ShipmentsTableItem, Integer> quantityColumn;
    @FXML private TableColumn<ShipmentsTableItem, String> dateColumn;

    private void openShipmentDialog(boolean isEdit, Shipments shipment) {
        try {
            FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-Shipments-dialog.fxml"));
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
        if (currentItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить поставку \"" + currentItem.getModelName() + " -> " + currentItem.getDealerName() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ShipmentsService().deleteById((long) currentItem.getShipmentId());
                shipmentsTable.getItems().remove(currentItem);
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
        if (selectedItem != null) {
            // Нужно получить объект Shipments по ID
            Shipments shipment = new ShipmentsService().findOne(selectedItem.getShipmentId());
            openShipmentDialog(true, shipment);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }

    public void updateList() {
        try {
            List<Object[]> results = new ShipmentsService().findAllWithNames();
            if (shipmentsObservable == null) {
                shipmentsObservable = FXCollections.observableArrayList();
                shipmentsTable.setItems(shipmentsObservable);
            }
            shipmentsObservable.clear();
            if (results != null) {
                for (Object[] row : results) {
                    shipmentsObservable.add(new ShipmentsTableItem(row));
                }
            }
            shipmentsTable.refresh();
        } catch (Exception e) {
            System.err.println("Ошибка в updateList():");
            e.printStackTrace();
        }
    }

    public void initialize() {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        dealerColumn.setCellValueFactory(new PropertyValueFactory<>("dealerName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        shipmentsObservable = FXCollections.observableArrayList();
        shipmentsTable.setItems(shipmentsObservable);
        updateList();
    }
}