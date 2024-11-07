package com.user_access_servlet.model;

public class Software {
    private int id;
    private String name;
    private String description;
    private String accessLevels;

    public Software() {}

    public Software(String name, String description, String accessLevels) {
        this.name = name;
        this.description = description;
        this.accessLevels = accessLevels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(String accessLevels) {
        this.accessLevels = accessLevels;
    }
}
