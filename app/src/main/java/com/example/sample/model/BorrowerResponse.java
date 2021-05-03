package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BorrowerResponse {
    @SerializedName("success")
    private Integer success;

    @SerializedName("borrowerslist")
    private ArrayList<Borrower> borrowersList;

    public boolean getSuccess() {
        return success == 1;
    }

    public ArrayList<Borrower> getBorrowersList() {
        return borrowersList;
    }

    public static class Borrower {
        @SerializedName("Borrowersid")
        private String borrowersId;

        //    @SerializedName("Branchid")
        //    private String branchid;

        @SerializedName("Borrowerscode")
        private String borrowersCode;

        @SerializedName("Firstname")
        private String firstName;

        @SerializedName("Fathername")
        private String fatherName;

        @SerializedName("Mobile")
        private String mobile;

        @SerializedName("Email")
        private String email;

        @SerializedName("Address")
        private String address;

        @SerializedName("Occupation")
        private String occupation;

        @SerializedName("Monthlyincome")
        private String monthlyIncome;

        @SerializedName("Proof")
        private String proof;

        @SerializedName("Refferecename")
        private String referenceName;

        @SerializedName("Refferecemobile")
        private String referencMobile;


        public String getBorrowersId() {
            return borrowersId;
        }

        public void setBorrowersId(String borrowersId) {
            this.borrowersId = borrowersId;
        }

        //    public String getBranchid() {
        //        return branchid;
        //    }
        //
        //    public void setBranchid(String branchid) {
        //        this.branchid = branchid;
        //    }

        public String getBorrowersCode() {
            return borrowersCode;
        }

        public void setBorrowersCode(String borrowersCode) {
            this.borrowersCode = borrowersCode;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(String monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getProof() {
            return proof;
        }

        public void setProof(String proof) {
            this.proof = proof;
        }

        public String getReferenceName() {
            return referenceName;
        }

        public void setReferenceName(String referenceName) {
            this.referenceName = referenceName;
        }

        public String getReferencMobile() {
            return referencMobile;
        }

        public void setReferencMobile(String referencMobile) {
            this.referencMobile = referencMobile;
        }

        @Override
        public String toString() {
            return "Borrower{" +
                    "borrowersId='" + borrowersId + '\'' +
                    ", borrowersCode='" + borrowersCode + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", fatherName='" + fatherName + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", occupation='" + occupation + '\'' +
                    ", monthlyIncome='" + monthlyIncome + '\'' +
                    ", proof='" + proof + '\'' +
                    ", referenceName='" + referenceName + '\'' +
                    ", referencMobile='" + referencMobile + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BorrowerResponse{" +
                "success=" + success +
                ", borrowersList=" + borrowersList +
                '}';
    }
}
