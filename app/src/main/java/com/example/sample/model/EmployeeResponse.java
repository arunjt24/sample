package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmployeeResponse {
    @SerializedName("success")
    private Integer success;

    @SerializedName("branchlist")
    private ArrayList<Employee> employeesList;

    public boolean getSuccess() {
        return success == 1;
    }

    public ArrayList<Employee> getEmployeesList() {
        return employeesList;
    }

    public class Employee {

        @SerializedName("Branchid")
        private String branchid;
        @SerializedName("Employeecode")
        private String employeecode;
        @SerializedName("Employeeid")
        private String employeeid;
        @SerializedName("Username")
        private String username;

        public String getBranchid() {
            return branchid;
        }

        public void setBranchid(String branchid) {
            this.branchid = branchid;
        }

        public String getEmployeecode() {
            return employeecode;
        }

        public void setEmployeecode(String employeecode) {
            this.employeecode = employeecode;
        }

        public String getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(String employeeid) {
            this.employeeid = employeeid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "branchid='" + branchid + '\'' +
                    ", employeecode='" + employeecode + '\'' +
                    ", employeeid='" + employeeid + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "success=" + success +
                ", employeesList=" + employeesList +
                '}';
    }
}
