package com.korpodrony.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JSONWriter {


    private String generateJsonString(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(object);
            return jsonInString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeJSONToFile(Path filePath, Object objectToMap) {

        try {
            String jsonString = generateJsonString(objectToMap);
            Files.write(filePath, jsonString.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Hmmm, zapis do pliku nie zadziałał");
        }
    }

}





