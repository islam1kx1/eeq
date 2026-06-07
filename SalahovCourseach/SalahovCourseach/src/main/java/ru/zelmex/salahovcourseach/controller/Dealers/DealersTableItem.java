package ru.zelmex.salahovcourseach.controller.Dealers;

import ru.zelmex.salahovcourseach.model.Dealers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DealersTableItem {
    private SimpleIntegerProperty dealerId;
    private SimpleStringProperty name;
    private SimpleStringProperty region;
    private SimpleStringProperty phone;
    private SimpleStringProperty contactPerson;
    private Dealers dealers;

    public DealersTableItem(Dealers dealers) {
        this.dealerId = new SimpleIntegerProperty(dealers.getDealerId());
        this.name = new SimpleStringProperty(dealers.getName());
        this.region = new SimpleStringProperty(dealers.getRegion());
        this.phone = new SimpleStringProperty(dealers.getPhone());
        this.contactPerson = new SimpleStringProperty(dealers.getContactPerson());
        this.dealers = dealers;
    }

    public int getDealerId() { return dealerId.get(); }
    public SimpleIntegerProperty dealerIdProperty() { return dealerId; }
    public void setDealerId(int dealerId) { this.dealerId.set(dealerId); }

    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getRegion() { return region.get(); }
    public SimpleStringProperty regionProperty() { return region; }
    public void setRegion(String region) { this.region.set(region); }

    public String getPhone() { return phone.get(); }
    public SimpleStringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }

    public String getContactPerson() { return contactPerson.get(); }
    public SimpleStringProperty contactPersonProperty() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson.set(contactPerson); }

    public Dealers getDealers() { return dealers; }
    public void setDealers(Dealers dealers) { this.dealers = dealers; }
}