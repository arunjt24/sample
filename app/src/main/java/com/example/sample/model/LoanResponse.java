package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoanResponse {

    @SerializedName("success")
    private Integer success;

    @SerializedName("loantlist")
    private ArrayList<Loan> loantlist = null;

    public boolean getSuccess() {
        return success == 1;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public ArrayList<Loan> getLoantlist() {
        return loantlist;
    }

    public void setLoantlist(ArrayList<Loan> loantlist) {
        this.loantlist = loantlist;
    }

    public class Loan {

        @SerializedName("Loanid")
        private String loanid;

        @SerializedName("Loancode")
        private String loancode;

        @SerializedName("Principalamount")
        private String principalamount;

        @SerializedName("Initiatedamount")
        private String initiatedamount;

        @SerializedName("Loanstatus")
        private String loanstatus;

        @SerializedName("Employeeid")
        private String employeeid;

        @SerializedName("Employeecod")
        private String employeecod;

        @SerializedName("Employeename")
        private String employeename;

        @SerializedName("Branchname")
        private String branchname;

        @SerializedName("Borrowerscode")
        private String borrowerscode;

        @SerializedName("Firstname")
        private String firstname;

        @SerializedName("Collectiontyp")
        private String collectiontyp;

        @SerializedName("StartDate")
        private String startDate;

        @SerializedName("EndDate")
        private String endDate;

        public String getLoanid() {
            return loanid;
        }

        public void setLoanid(String loanid) {
            this.loanid = loanid;
        }

        public String getLoancode() {
            return loancode;
        }

        public void setLoancode(String loancode) {
            this.loancode = loancode;
        }

        public String getPrincipalamount() {
            return principalamount;
        }

        public void setPrincipalamount(String principalamount) {
            this.principalamount = principalamount;
        }

        public String getInitiatedamount() {
            return initiatedamount;
        }

        public void setInitiatedamount(String initiatedamount) {
            this.initiatedamount = initiatedamount;
        }

        public String getLoanstatus() {
            return loanstatus;
        }

        public void setLoanstatus(String loanstatus) {
            this.loanstatus = loanstatus;
        }

        public String getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(String employeeid) {
            this.employeeid = employeeid;
        }

        public String getEmployeecod() {
            return employeecod;
        }

        public void setEmployeecod(String employeecod) {
            this.employeecod = employeecod;
        }

        public String getEmployeename() {
            return employeename;
        }

        public void setEmployeename(String employeename) {
            this.employeename = employeename;
        }

        public String getBranchname() {
            return branchname;
        }

        public void setBranchname(String branchname) {
            this.branchname = branchname;
        }

        public String getBorrowerscode() {
            return borrowerscode;
        }

        public void setBorrowerscode(String borrowerscode) {
            this.borrowerscode = borrowerscode;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getCollectiontyp() {
            return collectiontyp;
        }

        public void setCollectiontyp(String collectiontyp) {
            this.collectiontyp = collectiontyp;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Loan{" +
                    "loanid='" + loanid + '\'' +
                    ", loancode='" + loancode + '\'' +
                    ", principalamount='" + principalamount + '\'' +
                    ", initiatedamount='" + initiatedamount + '\'' +
                    ", loanstatus='" + loanstatus + '\'' +
                    ", employeeid='" + employeeid + '\'' +
                    ", employeecod='" + employeecod + '\'' +
                    ", employeename='" + employeename + '\'' +
                    ", branchname='" + branchname + '\'' +
                    ", borrowerscode='" + borrowerscode + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", collectiontyp='" + collectiontyp + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoanResponse{" +
                "success=" + success +
                ", loantlist=" + loantlist +
                '}';
    }
}