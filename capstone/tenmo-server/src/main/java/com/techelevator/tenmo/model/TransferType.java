package com.techelevator.tenmo.model;

public class TransferType {
    private int type_id;
    private String type;

    public TransferType(int type_id, String type) {
        this.type_id = type_id;
        this.type = type;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getType_id() {
        return type_id;
    }

    public String getType() {
        return type;
    }


}
