package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CollectionsResponse {

    @SerializedName("success")
    private Integer success;

    @SerializedName("collectionlist")
    private ArrayList<CollectionsResponse.Collection> collectionlist;

    public ArrayList<CollectionsResponse.Collection> getCollectionList() {
        return collectionlist;
    }

    public boolean getSuccess() {
        return success == 1;
    }

    @Override
    public String toString() {
        return "CollectionsResponse{" +
                "success=" + success +
                ", collectionlist=" + collectionlist +
                '}';
    }

    public static class Collection {
        @SerializedName("Collectiondate")

        private String collectiondate;
        @SerializedName("Loancode")

        private String loancode;
        @SerializedName("Principalamount")

        private String principalamount;
        @SerializedName("Initiatedamount")

        private String initiatedamount;
        @SerializedName("Intrest")

        private String intrest;
        @SerializedName("Employeeid")

        private String employeeid;
        @SerializedName("Employeecod")

        private Object employeecod;
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
        @SerializedName("Noofdays")

        private String noofdays;
        @SerializedName("Pendinginstallments")

        private String pendinginstallments;
        @SerializedName("Amount")

        private String amount;
        @SerializedName("Balance")

        private String balance;
        @SerializedName("Collection")

        private String collection;

        public String getCollectiondate() {
            return collectiondate;
        }

        public void setCollectiondate(String collectiondate) {
            this.collectiondate = collectiondate;
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

        public String getIntrest() {
            return intrest;
        }

        public void setIntrest(String intrest) {
            this.intrest = intrest;
        }

        public String getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(String employeeid) {
            this.employeeid = employeeid;
        }

        public Object getEmployeecod() {
            return employeecod;
        }

        public void setEmployeecod(Object employeecod) {
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

        public String getNoofdays() {
            return noofdays;
        }

        public void setNoofdays(String noofdays) {
            this.noofdays = noofdays;
        }

        public String getPendinginstallments() {
            return pendinginstallments;
        }

        public void setPendinginstallments(String pendinginstallments) {
            this.pendinginstallments = pendinginstallments;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        @Override
        public String toString() {
            return "Collection{" +
                    "collectiondate='" + collectiondate + '\'' +
                    ", loancode='" + loancode + '\'' +
                    ", principalamount='" + principalamount + '\'' +
                    ", initiatedamount='" + initiatedamount + '\'' +
                    ", intrest='" + intrest + '\'' +
                    ", employeeid='" + employeeid + '\'' +
                    ", employeecod=" + employeecod +
                    ", employeename='" + employeename + '\'' +
                    ", branchname='" + branchname + '\'' +
                    ", borrowerscode='" + borrowerscode + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", collectiontyp='" + collectiontyp + '\'' +
                    ", noofdays='" + noofdays + '\'' +
                    ", pendinginstallments='" + pendinginstallments + '\'' +
                    ", amount='" + amount + '\'' +
                    ", balance='" + balance + '\'' +
                    ", collection='" + collection + '\'' +
                    '}';
        }
    }
}