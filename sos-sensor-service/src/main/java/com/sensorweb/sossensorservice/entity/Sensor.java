package com.sensorweb.sossensorservice.entity;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private String id;
    private String name;
    private String description;
    private String filePath;
    private Keyword keywords;
    private List<Identifier> identification = new ArrayList<>();
    private List<Classifier> classification = new ArrayList<>();
    private ValidTime validTime;
    private List<Characteristic> characteristics = new ArrayList<>();
    private List<Capability> capabilities = new ArrayList<>();
    private Position position;
    private Contact contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ValidTime getValidTime() {
        return validTime;
    }

    public void setValidTime(ValidTime validTime) {
        this.validTime = validTime;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Keyword getKeywords() {
        return keywords;
    }

    public void setKeywords(Keyword keywords) {
        this.keywords = keywords;
    }

    public List<Identifier> getIdentification() {
        return identification;
    }

    public void setIdentification(List<Identifier> identification) {
        this.identification = identification;
    }

    public List<Classifier> getClassification() {
        return classification;
    }

    public void setClassification(List<Classifier> classification) {
        this.classification = classification;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
