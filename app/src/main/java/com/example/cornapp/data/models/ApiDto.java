package com.example.cornapp.data.models;

import org.json.JSONObject;

public class ApiDto {
    private String status = "";
    private int code = 0;
    private JSONObject result;

    public ApiDto(String status, int code, JSONObject result) {
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

    public JSONObject getResult() {
        return result;
    }

}
