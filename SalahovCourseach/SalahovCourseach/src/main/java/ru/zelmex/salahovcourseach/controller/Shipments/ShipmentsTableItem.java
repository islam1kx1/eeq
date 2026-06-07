package ru.zelmex.salahovcourseach.controller.Shipments;

import ru.zelmex.salahovcourseach.model.Shipments;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class ShipmentsTableItem {
    private SimpleIntegerProperty shipmentId;
    private SimpleIntegerProperty modelId;
    private SimpleIntegerProperty dealerId;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty date;
    private Shipments shipment;

    public ShipmentsTableItem(Shipments shipment) {
        this.shipmentId = new SimpleIntegerProperty(shipment.getShipmentId());
        this.modelId = new SimpleIntegerProperty(shipment.getModelId());
        this.dealerId = new SimpleIntegerProperty(shipment.getDealerId());
        this.quantity = new SimpleIntegerProperty(shipment.getQuantity());
        this.date = new SimpleStringProperty(shipment.getDate().toString());
        this.shipment = shipment;
    }

    public int getShipmentId() {
        return shipmentId.get();
    }

    public SimpleIntegerProperty shipmentIdProperty() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId.set(shipmentId);
    }

    public int getModelId() {
        return modelId.get();
    }

    public SimpleIntegerProperty modelIdProperty() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId.set(modelId);
    }

    public int getDealerId() {
        return dealerId.get();
    }

    public SimpleIntegerProperty dealerIdProperty() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId.set(dealerId);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public Shipments getShipment() {
        return shipment;
    }

    public void setShipment(Shipments shipment) {
        this.shipment = shipment;
    }
}