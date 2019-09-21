package com.korpodrony.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Json {
    private Json() {
    }


    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> boolean writeSetToFile(String path, Set<T> objects) {
        File file = new File(path);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, objects);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T>Set readSetFromFile(String path, Class<T>genericClass) throws ClassNotFoundException, IOException {
        Set<T> set = objectMapper.readValue(new File(path), objectMapper .getTypeFactory().constructCollectionType(Set.class, Class.forName(genericClass.getName())));
        return set;
    }
}
