package ru.zelmex.salahovcourseach.controller.ModelLines;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.zelmex.salahovcourseach.model.ModelLines;

public class ModelLinesTableItem {

    private SimpleIntegerProperty modelId;
    private SimpleStringProperty name;
    private SimpleStringProperty type;
    private SimpleStringProperty specifications;
    private SimpleDoubleProperty price;
    private ModelLines modelLines;

    public ModelLinesTableItem(ModelLines modelLines) {
        this.modelId = new SimpleIntegerProperty(modelLines.getModelId());
        this.name = new SimpleStringProperty(modelLines.getName());
        this.type = new SimpleStringProperty(modelLines.getType());
        this.specifications = new SimpleStringProperty(modelLines.getSpecifications());

        // Конвертируем BigDecimal в double для отображения
        double priceValue = modelLines.getPrice() != null ? modelLines.getPrice().doubleValue() : 0.0;
        this.price = new SimpleDoubleProperty(priceValue);

        this.modelLines = modelLines;
    }

    // Геттеры и сеттеры
    public int getModelId() {
        return modelId.get();
    }

    public SimpleIntegerProperty modelIdProperty() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId.set(modelId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSpecifications() {
        return specifications.get();
    }

    public SimpleStringProperty specificationsProperty() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications.set(specifications);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public ModelLines getModelLines() {
        return modelLines;
    }

    public void setModelLines(ModelLines modelLines) {
        this.modelLines = modelLines;
        // Обновляем все поля
        this.modelId.set(modelLines.getModelId());
        this.name.set(modelLines.getName());
        this.type.set(modelLines.getType());
        this.specifications.set(modelLines.getSpecifications());
        double priceValue = modelLines.getPrice() != null ? modelLines.getPrice().doubleValue() : 0.0;
        this.price.set(priceValue);
    }
}