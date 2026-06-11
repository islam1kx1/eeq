package ru.zelmex.salahovcourseach.controller.Dealers;

import ru.zelmex.salahovcourseach.motorcycleCompany;
import ru.zelmex.salahovcourseach.model.Dealers;
import ru.zelmex.salahovcourseach.service.DealersService;
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

public class DealersController {
    private List<Dealers> dealersList;

    @FXML
    private TableView<DealersTableItem> dealersTable;
    @FXML
    private TableColumn<DealersTableItem, String> nameColumn;
    @FXML
    private TableColumn<DealersTableItem, String> regionColumn;
    @FXML
    private TableColumn<DealersTableItem, String> phoneColumn;
    @FXML
    private TableColumn<DealersTableItem, String> contactPersonColumn;

    private ObservableList<DealersTableItem> dealersObservable;

    // ========== МЕТОДЫ ДЛЯ КНОПОК НАВИГАЦИИ (ИЗ FXML) ==========

    @FXML
    void onClickMotorcycles(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.modelLines);
    }

    @FXML
    void onClickDealers(ActionEvent event) {
        updateList();
    }

    @FXML
    void onClickShipments(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.shipments);
    }

    @FXML
    void powerOff(ActionEvent event) {
        motorcycleCompany.primaryStage.close();
    }

    // ========== CRUD ОПЕРАЦИИ ==========

    @FXML
    void btnAddDealers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-Dealers-dialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(motorcycleCompany.primaryStage);
            dialogStage.setMinWidth(400);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить дилера");
            AddEditDealersDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            System.out.println("Ошибка открытия окна: " + e.getMessage());
        }
    }

    @FXML
    void btnEditDealers(ActionEvent event) {

    }

    @FXML
    void btnDeleteDealers(ActionEvent event) {
        DealersTableItem currentItem = dealersTable.getSelectionModel().getSelectedItem();
        int currentItemId = dealersTable.getSelectionModel().getSelectedIndex();
        if (currentItemId != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить \"" + currentItem.getName() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new DealersService().delete(currentItem.getDealers());
                dealersTable.getItems().remove(currentItemId);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для удаления");
            alert.showAndWait();
        }
    }

    @FXML
    void btnUpdateDealers(ActionEvent event) {
        DealersTableItem currentItem = dealersTable.getSelectionModel().getSelectedItem();
        int currentItemId = dealersTable.getSelectionModel().getSelectedIndex();
        if (currentItemId != -1) {
            try {
                FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-Dealers-dialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(motorcycleCompany.primaryStage);
                dialogStage.setMinWidth(400);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать дилера");
                AddEditDealersDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getDealers());
                dialogStage.showAndWait();
                updateList();
            } catch (IOException e) {
                System.out.println("Ошибка открытия окна: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }

    public void updateList() {
        try {
            dealersList = new DealersService().findAll();
            if (dealersObservable == null) {
                dealersObservable = FXCollections.observableArrayList();
                dealersTable.setItems(dealersObservable);
            }
            dealersObservable.clear();
            if (dealersList != null) {
                for (Dealers dealer : dealersList) {
                    dealersObservable.add(new DealersTableItem(dealer));
                }
            }
            dealersTable.refresh();
        } catch (Exception e) {
            System.err.println("Ошибка в updateList():");
            e.printStackTrace();
        }
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contactPersonColumn.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));

        dealersObservable = FXCollections.observableArrayList();
        dealersTable.setItems(dealersObservable);
        updateList();
    }
}