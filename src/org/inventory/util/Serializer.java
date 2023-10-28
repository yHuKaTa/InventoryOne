package org.inventory.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Serializer<T> {
    private final ObjectMapper mapper;

    public Serializer() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void writeToJsonFile(String fileLocation, T data) {
        Path path = Paths.get(fileLocation);
        String filePath = path.toAbsolutePath().toString();
        try (Writer writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, data);
        } catch (IOException e) {
            System.out.println("Your information aren't saved! Please provide valid json file!");
            e.printStackTrace();
        }
    }

    public T readFromJsonFile(String fileLocation, TypeReference<T> valueType) {
        T temp = null;
        Path path = Paths.get(fileLocation);
        String filePath = path.toAbsolutePath().toString();
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            if (jsonContent.isEmpty()) {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Could not read from file " + filePath);
        }
        try {
            temp = mapper.readValue(jsonContent.toString(), valueType);
        } catch (JsonProcessingException e) {
            System.out.printf("Could not convert data from file! %s\n", e.getOriginalMessage());
        }
        return temp;
    }
}
