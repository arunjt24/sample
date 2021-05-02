package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchResponse {

    @SerializedName("success")
    private Integer success;

    @SerializedName("branchlist")
    private List<Branch> branchList = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }

    public class Branch {

        @SerializedName("intBranchid")
        private String branchId;
        @SerializedName("Branchname")
        private String branchName;

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        @Override
        public String toString() {
            return "Branch{" +
                    "branchId='" + branchId + '\'' +
                    ", branchName='" + branchName + '\'' +
                    '}';
        }
    }
}