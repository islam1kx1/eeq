package ru.zelmex.salahovcourseach.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "\"ModelLines\"")
public class ModelLines {

    @Id
    @Column(name = "modelid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "specifications", columnDefinition = "TEXT")
    private String specifications;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    // Конструкторы
    public ModelLines() {}

    public ModelLines(String name, String type, String specifications, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.specifications = specifications;
        this.price = price;
    }

    // Геттеры и сеттеры
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Название модели не может быть пустым!");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Цена не может быть отрицательной!");
        }
    }

    @Override
    public String toString() {
        return name + " - " + price + " ₽";
    }
}