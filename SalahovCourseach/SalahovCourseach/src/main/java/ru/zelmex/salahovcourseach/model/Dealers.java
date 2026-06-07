package ru.zelmex.salahovcourseach.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "\"Dealers\"")
public class Dealers {

    @Id
    @Column(name = "dealerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dealerId;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;

    @Column(name = "phone")
    private String phone;

    @Column(name = "contactperson")
    private String contactPerson;

    // Геттеры и сеттеры

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Название дилера не должно быть пустым");
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        if (region != null && !region.trim().isEmpty()) {
            this.region = region;
        } else {
            throw new IllegalArgumentException("Регион не должен быть пустым");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Телефон не должен быть пустым");
        }
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        if (contactPerson != null && !contactPerson.trim().isEmpty()) {
            this.contactPerson = contactPerson;
        } else {
            throw new IllegalArgumentException("Контактное лицо не должно быть пустым");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}