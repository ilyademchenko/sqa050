package com.luxoft.web.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

//    private static final String COMMA_DELIMITER = ",";

    public static List<String> obtainListFromCsv(String filePath) {
        List<String> result = new ArrayList<>();
//        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String value = line;
                result.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
