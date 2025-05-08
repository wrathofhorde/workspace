package com.wrath.selenium.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Config {
    private String url;
    private String email;
    private String password;
    private String mainPopup;
    private String mainPopupButton;

    public static Config loadFromJson(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), Config.class);
        } catch (IOException e) {
            System.out.println("Config.loadFromJson()");
        }
        return null;
    }

    public void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filePath), this);
    }
}
