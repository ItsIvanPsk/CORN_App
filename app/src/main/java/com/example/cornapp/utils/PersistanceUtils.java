package com.example.cornapp.utils;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersistanceUtils {

    public static void writeOnCsv(String filename, String content) {
        try {
            File csvFile = new File(filename);
            FileWriter csvWriter = new FileWriter(csvFile);
            csvWriter.write(content);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("5cos", "post");
    }

}
