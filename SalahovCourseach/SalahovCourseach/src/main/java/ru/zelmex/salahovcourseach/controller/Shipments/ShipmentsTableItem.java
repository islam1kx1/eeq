package ru.zelmex.salahovcourseach.controller.Shipments;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShipmentsTableItem {
    private SimpleIntegerProperty shipmentId;
    private SimpleStringProperty modelName;
    private SimpleStringProperty dealerName;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty date;
    private Object shipment; // можно хранить ID или объект

    public ShipmentsTableItem(Object[] row) {
        this.shipmentId = new SimpleIntegerProperty((Integer) row[0]);
        this.modelName = new SimpleStringProperty((String) row[1]);
        this.dealerName = new SimpleStringProperty((String) row[2]);
        this.quantity = new SimpleIntegerProperty((Integer) row[3]);
        this.date = new SimpleStringProperty(row[4] != null ? row[4].toString() : "");
    }

    public int getShipmentId() { return shipmentId.get(); }
    public SimpleIntegerProperty shipmentIdProperty() { return shipmentId; }

    public String getModelName() { return modelName.get(); }
    public SimpleStringProperty modelNameProperty() { return modelName; }

    public String getDealerName() { return dealerName.get(); }
    public SimpleStringProperty dealerNameProperty() { return dealerName; }

    public int getQuantity() { return quantity.get(); }
    public SimpleIntegerProperty quantityProperty() { return quantity; }

    public String getDate() { return date.get(); }
    public SimpleStringProperty dateProperty() { return date; }
}