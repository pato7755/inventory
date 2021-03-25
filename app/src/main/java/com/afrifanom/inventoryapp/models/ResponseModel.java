package com.afrifanom.inventoryapp.models;

public class ResponseModel {

    private String responseCode;
    private String responseMessage;
    private String data;

    public ResponseModel() {
    }

    public ResponseModel(String responseCode, String responseMessage, String data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
