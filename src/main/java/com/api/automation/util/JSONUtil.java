package com.api.automation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JSONUtil {

    public static Map<String, Object> read(String jsonFilePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(jsonFilePath));
        HashMap<String, Object> mapping = new ObjectMapper().readValue(new String(jsonData), HashMap.class);
        return mapping;
    }
}
