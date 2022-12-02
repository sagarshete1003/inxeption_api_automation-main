package com.api.automation.util;

import com.api.automation.api.ApiUtil;
import com.api.automation.exception.AutomationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class PayloadUtil {
    private static final String PAYLOADS_FILE_PATH = "src/test/resources/payloads/%s.json";
    public static String generatePayload(String payloadName, Map<String, Object> params) throws AutomationException {
        String payload = readFileAsString(String.format(PAYLOADS_FILE_PATH, payloadName));
        Set<String> paramSet = params.keySet();
        for(String param: paramSet) {
            if(!param.equalsIgnoreCase("payload")) {
                if(payload.contains("\\$\\{"+param+"\\}"))
                    throw new AutomationException(String.format("Payload - %s doesn't contains parameter - %s", payload, param));
                payload = payload.replaceAll("\\$\\{"+param+"\\}", ApiUtil.processProperty(params.get(param).toString()).toString());
            }
        }
        return payload;
    }

    public static String readFileAsString(String file) throws AutomationException
    {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutomationException("Unable to find payload json file - "+file);
        }
    }
}
