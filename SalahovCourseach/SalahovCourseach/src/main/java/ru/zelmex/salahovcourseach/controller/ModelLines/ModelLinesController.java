package ru.zelmex.salahovcourseach.controller.ModelLines;

import ru.zelmex.salahovcourseach.motorcycleCompany;
import ru.zelmex.salahovcourseach.model.ModelLines;
import ru.zelmex.salahovcourseach.service.ModelLinesService;
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

public class ModelLinesController {
    private List<ModelLines> modelLinesList;

    @FXML private TableView<ModelLinesTableItem> clientsTable;
    @FXML private TableColumn<ModelLinesTableItem, String> nameColumn;
    @FXML private TableColumn<ModelLinesTableItem, String> typeColumn;
    @FXML private TableColumn<ModelLinesTableItem, String> specificationsColumn;
    @FXML private TableColumn<ModelLinesTableItem, String> priceColumn;

    private ObservableList<ModelLinesTableItem> modelLinesObservable;

    @FXML
    void onClickMotorcycles(ActionEvent event) {
        updateList();
    }

    @FXML
    void onClickDealers(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.dealers);
    }

    @FXML
    void onClickShipments(ActionEvent event) {
        motorcycleCompany.primaryStage.setScene(motorcycleCompany.shipments);
    }

    @FXML
    void powerOff(ActionEvent event) {
        motorcycleCompany.primaryStage.close();
    }

    @FXML
    void addModel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-modellines-dialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(motorcycleCompany.primaryStage);
            dialogStage.setMinWidth(400);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить модель");
            AddEditModelLinesDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            System.out.println("Ошибка открытия окна: " + e.getMessage());
        }
    }

    @FXML
    void editModel(ActionEvent event) {
        updateList();
    }

    @FXML
    void deleteModel(ActionEvent event) {
        ModelLinesTableItem currentItem = clientsTable.getSelectionModel().getSelectedItem();
        int currentItemId = clientsTable.getSelectionModel().getSelectedIndex();
        if (currentItemId != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить \"" + currentItem.getName() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ModelLinesService().delete(currentItem.getModelLines());
                clientsTable.getItems().remove(currentItemId);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для удаления");
            alert.showAndWait();
        }
    }

    @FXML
    void updateModels(ActionEvent event) {
        ModelLinesTableItem currentItem = clientsTable.getSelectionModel().getSelectedItem();
        int currentItemId = clientsTable.getSelectionModel().getSelectedIndex();
        if (currentItemId != -1) {
            try {
                FXMLLoader loader = new FXMLLoader(motorcycleCompany.class.getResource("add-edit-modellines-dialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(motorcycleCompany.primaryStage);
                dialogStage.setMinWidth(400);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать модель");
                AddEditModelLinesDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getModelLines());
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
            modelLinesList = new ModelLinesService().findAll();
            if (modelLinesObservable == null) {
                modelLinesObservable = FXCollections.observableArrayList();
                clientsTable.setItems(modelLinesObservable);
            }
            modelLinesObservable.clear();
            if (modelLinesList != null) {
                for (ModelLines model : modelLinesList) {
                    modelLinesObservable.add(new ModelLinesTableItem(model));
                }
            }
            clientsTable.refresh();
        } catch (Exception e) {
            System.err.println("Ошибка в updateList():");
            e.printStackTrace();
        }
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        specificationsColumn.setCellValueFactory(new PropertyValueFactory<>("specifications"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        modelLinesObservable = FXCollections.observableArrayList();
        clientsTable.setItems(modelLinesObservable);
        updateList();
    }
}