package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CollectionTypeResponse {
    @SerializedName("success")
    private Integer success;

    @SerializedName("collectiontypelist")
    private ArrayList<Type> collectionType;

    public ArrayList<Type> getCollectionType() {
        return collectionType;
    }
    public class Type {

        @SerializedName("intCollectiontypeid")
        private String collectionTypeId;
        @SerializedName("vCollectiontype")
        private String collectionType;

        public String getCollectionTypeId() {
            return collectionTypeId;
        }

        public void setCollectionTypeId(String collectionTypeId) {
            this.collectionTypeId = collectionTypeId;
        }

        public String getCollectionType() {
            return collectionType;
        }

        public void setCollectionType(String collectionType) {
            this.collectionType = collectionType;
        }

    }
}
