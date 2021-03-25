package com.afrifanom.inventoryapp.models;

import java.util.List;

public class ShoesResponseModel {

    private String responseCode;
    private String responseMessage;
    private List<ShoeModel> data;

    public ShoesResponseModel() {
    }

    public ShoesResponseModel(String responseCode, String responseMessage, List<ShoeModel> data) {
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


    public List<ShoeModel> getData() {

        return data;
    }

    public void setData(List<ShoeModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShoesResponseModel{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", data=" + data +
                '}';
    }

}

class data{
    public int id;
    public String brand;
    public String size;
    public String price;
    public String color;
}
