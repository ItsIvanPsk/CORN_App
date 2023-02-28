package com.example.cornapp.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class JsonUtils {
    public static void saveDataToFile(Context context, String fileName, ArrayList<String> dataList) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (String data : dataList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", data);
                jsonArray.put(jsonObject);
            }
            String jsonString = jsonArray.toString();
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readDataFromFile(Context context, String fileName) {
        ArrayList<String> dataList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String jsonString = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String data = jsonObject.getString("data");
                dataList.add(data);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
