package com.user_access_servlet.model;


public class Request {
    private int id;
    private String employeeName;
    private String softwareName;
    private String accessType;
    private String reason;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getSoftwareName() { return softwareName; }
    public void setSoftwareName(String softwareName) { this.softwareName = softwareName; }

    public String getAccessType() { return accessType; }
    public void setAccessType(String accessType) { this.accessType = accessType; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
