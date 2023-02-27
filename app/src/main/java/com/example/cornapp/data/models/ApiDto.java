package com.example.cornapp.data.models;

import org.json.JSONObject;

public class ApiDto {
    private String status = "", result = "";
    private int code = 0;

    public ApiDto(String status, int code, String result) {
        this.status = status;
        this.code = code;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

}
