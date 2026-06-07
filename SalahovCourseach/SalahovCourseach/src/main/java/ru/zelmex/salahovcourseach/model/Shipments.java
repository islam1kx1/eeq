package ru.zelmex.salahovcourseach.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "\"Shipments\"")
public class Shipments {

    @Id
    @Column(name = "shipmentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipmentId;

    @Column(name = "modelid")
    private Integer modelId;

    @Column(name = "dealerid")
    private Integer dealerId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date")
    private LocalDate date;

    // Геттеры и сеттеры

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantityText) {
        if (quantityText != null && !quantityText.trim().isEmpty() && quantityText.matches("\\d+")) {
            int q = Integer.parseInt(quantityText);
            if (q > 0) {
                this.quantity = q;
            } else {
                throw new IllegalArgumentException("Количество должно быть больше 0!");
            }
        } else {
            throw new IllegalArgumentException("Количество должно быть целым положительным числом!");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date != null) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("Дата не должна быть пустой!");
        }
    }

    @Override
    public String toString() {
        return "Поставка #" + shipmentId;
    }
}