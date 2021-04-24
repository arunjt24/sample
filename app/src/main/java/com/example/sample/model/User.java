package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("success")
    private Integer success;

    @SerializedName("message")
    private String message;

    @SerializedName("Employeeid")
    private String employeeId;

    @SerializedName("Branchid")
    private String branchId;

    @SerializedName("Employeecode")
    private String employeecode;

    @SerializedName("Firstname")
    private String firstName;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("Address")
    private String address;

    @SerializedName("Image")
    private String image;

    @SerializedName("Status")
    private String status;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", employeecode='" + employeecode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}