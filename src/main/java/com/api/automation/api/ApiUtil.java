package com.api.automation.api;

import com.api.automation.constants.Constants;
import com.api.automation.exception.AutomationException;
import com.api.automation.util.PayloadUtil;
import com.api.automation.util.ReportLogger;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.*;
import java.util.regex.Pattern;

import com.api.automation.util.DataCache;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiUtil {
    public static ReportLogger reportLogger = ReportLogger.getInstance();

    public static boolean verifyStatusCode(Response res, int statusCode) throws AutomationException {
        try {
            reportLogger.log("response::::::"+res.asString());
            int resCode = res.getStatusCode();
            if(resCode==statusCode) {
                reportLogger.log("Actual Status Code is '"+resCode+"' And Expected Status Code is '"+statusCode+"'");
                return true;
            } else {
                reportLogger.log("Actual Status Code is '"+resCode+"' But Expected Status Code is '"+statusCode+"'");
                throw new AutomationException("Status Code Verification is Failed <br> " + "Actual Status Code is '"+resCode+"' But Expected Status Code is '"+statusCode+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyErrorCode(Response res,String jsonPath, int statusCode) throws AutomationException {
        try {
            int resCode=res.getBody().jsonPath().get(jsonPath);
            if(resCode==statusCode) {
                reportLogger.log("Actual Status Code is '"+resCode+"' And Expected Status Code is '"+statusCode+"'");
            }
            else {
                reportLogger.log("Actual Status Code is '"+resCode+"' But Expected Status Code is '"+statusCode+"'");
                throw new AutomationException("Status Code Verification is Failed <br>" + "Actual Status Code is '"+resCode+"' But Expected Status Code is '"+statusCode+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValues(Response res, String jsonPath, boolean expectedValue) throws AutomationException {
        try {
            boolean actualValue=res.getBody().jsonPath().get(jsonPath);
            if(actualValue==expectedValue) {
                reportLogger.log("Actual '"+jsonPath+"' value is - '"+actualValue+"' And Expected value - '"+expectedValue+"'");
            }
            else {
                reportLogger.log("Actual '"+jsonPath+"' value is - "+actualValue+"' but Expected value - '"+expectedValue+"'");
                throw new AutomationException("Response value for '"+jsonPath+"' Verification is Failed <br> "+"Actual '"+jsonPath+"' value is - "+actualValue+"' but Expected value - '"+expectedValue+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValue(Response res,String jsonPath, String expectedValue) throws AutomationException {
        try {
            Object actualValue=res.getBody().jsonPath().get(jsonPath);
            System.out.println("actualValue::::"+actualValue);
            if(actualValue.equals(expectedValue)) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected Value is '"+expectedValue+"'");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValues(Response res,String jsonPath, String[] expectedValue) throws AutomationException {
        // TODO: Figure out what to put in the feature file for this method to work properly

        try {
            String actualValue=res.getBody().jsonPath().get(jsonPath);

            System.out.println("actualValue::::"+actualValue);
            List<String> expectedValues = Arrays.asList(expectedValue);
            if(expectedValues.contains(actualValue)) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected Value can be one in '"+expectedValue+"'");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value can be one in '"+expectedValue+"'");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value can be one in '"+expectedValue+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValueNotNull(Response res,String jsonPath) throws AutomationException {
        try {
            Object actualValue=res.getBody().jsonPath().get(jsonPath);
            System.out.println("actualValue::::"+actualValue);
            if(actualValue!=null) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should not be null.");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should not be null.");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should not be null.");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    /**
     * This method validates that a given property in the JSON response is NULL
     * @param res request response
     * @param jsonPath path to property in question
     * @throws AutomationException
     */
    public static void verifyJsonValueIsNull(Response res,String jsonPath) throws AutomationException {
        try {
            Object actualValue=res.getBody().jsonPath().get(jsonPath);
            if(actualValue==null) {
                reportLogger.log(jsonPath+" ===> Actual Value 'IS NULL' And Expected it should be null.");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be null.");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be null.");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValueNotEmpty(Response res,String jsonPath) throws AutomationException {
        verifyJsonValueNotNull(res,jsonPath);
    }

    public static void verifyJsonValueEmpty(Response res,String jsonPath) throws AutomationException {
        try {
            String actualValue=res.getBody().jsonPath().get(jsonPath);
            System.out.println("actualValue::::"+actualValue);
            if(actualValue!=null && actualValue.isEmpty()) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    /**
     * This method validates that a particular property in the JSON response has a value of '[]'
     * @param res request response
     * @param jsonPath path to the property being validated
     * @throws AutomationException
     */
    public static void verifyJsonValueEmptyArray(Response res,String jsonPath) throws AutomationException {
        try {
            ArrayList<String> actualValue=res.getBody().jsonPath().get(jsonPath);
            System.out.println("actualValue::::"+actualValue);
            if(actualValue!=null && actualValue.isEmpty()) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected it should be empty.");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyJsonValue(Response res,String jsonPath, int expectedValue) throws AutomationException {
        try {
            JsonPath path=res.getBody().jsonPath();
            int actualValue=path.get(jsonPath);
            System.out.println("actualValue::::"+actualValue);
            if(actualValue==expectedValue) {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' And Expected Value is '"+expectedValue+"'");
            }
            else {
                reportLogger.log(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
                throw new AutomationException(jsonPath+" ===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
            }
        } catch (Exception e) {
            throw new AutomationException(e.getMessage());
        }
    }

    public static void verifyEqual(String actualValue, String expectedValue) throws AutomationException {
        if(!actualValue.equalsIgnoreCase(expectedValue)) {
            reportLogger.log("===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
            throw new AutomationException("===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
        }
    }

    public static String readProperty(String key) {
        return System.getProperty(key);
    }

    public static Object processProperty(String key) {
        Object value = null;

        // We need to be able to use captured variables to create test conditions,
        // so we use the C++ << operator as a way to concatenate strings but process them individually.
        // This way we can test things like dynamic file names without hard coding them
        if (key.contains("<<")) {
            StringBuilder strBldr = new StringBuilder();
            String[] parts = key.split("<<");
            for (String part: parts) {
                strBldr.append(processProperty(part.trim()));
            }
            return strBldr.toString();
        }

        if(key.contains("$timestamp"))//replace with current timestamp
            value = key.replace("$timestamp", Long.toString(System.currentTimeMillis()));
        else if(key.contains("$uuid"))//replace with uuid
            value = key.replace("$uuid", UUID.randomUUID().toString());
        else if(key.startsWith("^@")) {

            // The DataCache for storing variables in feature files is based on strings,
            // so this lets the user cast a stored variable as an integer
            String propStr = DataCache.readVariable(key.substring(1, key.length()))!=null?DataCache.readVariable(key.substring(1, key.length())):key;
            value = Long.parseLong(propStr);

        }
        else if(key.startsWith("@"))//for variable
            value = DataCache.readVariable(key)!=null?DataCache.readVariable(key):key;
        else if(key.startsWith("$"))//for property
            value = readProperty(key.substring(1,key.length()));
        else if(key.startsWith("^"))//for number
            value = Long.parseLong(key.substring(1,key.length()));
        else if(key.startsWith("^^"))//for decimal
            value = Double.parseDouble(key.substring(1,key.length()));
        else if(key.startsWith("*"))//for boolean
            value = Boolean.parseBoolean(key.substring(1,key.length()));
        else if(key.startsWith("[") && key.endsWith("]"))
            value = getStringToStringArray(key);
        else if(key.contains("${")) {
            while(key.contains("${")) {
                String paramKey = key.substring(key.indexOf("${") + 2, key.indexOf("}"));
                String paramVal = DataCache.readVariable(paramKey) != null ? DataCache.readVariable(paramKey) : paramKey;
                paramKey = "${" + paramKey + "}";
                value = key.replace(paramKey, paramVal);
                key=value.toString();
            }
        }
        else //for string
            value = readProperty(key)!=null?readProperty(key):key;
        return value==null?new String():value;
    }

    public static String[] getStringToStringArray(String values) {
        if(values.startsWith("[") && values.endsWith("]")) {
            values = values.replaceAll(", ",",");
            values = values.substring(1, values.length()-1);
        }
        return values.split(",");
    }

    public static JSONObject createPayloadFromDataTable(DataTable dataTable) throws ParseException {
        if(dataTable==null || dataTable.isEmpty())
            return new JSONObject();
        final Pattern pattern = Pattern.compile("\\[[^\\[]*\\]", Pattern.MULTILINE);
        JSONObject payload = new JSONObject();
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        if(dataMap==null || dataMap.isEmpty())
            return new JSONObject();
        if(dataMap.get("requestBody")!=null) {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(dataMap.get("requestBody"));
        }
        for(String key: dataMap.keySet()) {
            if(key.contains(".")) {
                JSONObject parent = payload;
                StringTokenizer st = new StringTokenizer(key,".");
                while (st.hasMoreTokens()) {
                    String nestedObjectKey = st.nextToken();
                    JSONObject nestedObject;
                    if(pattern.matcher(nestedObjectKey).find()) {
                        String arrayName = nestedObjectKey.substring(0, nestedObjectKey.indexOf("["));
                        int arrayIndex = Integer.parseInt(nestedObjectKey.substring(nestedObjectKey.indexOf("[")+1, nestedObjectKey.length()-1));
                        JSONArray nestedArray = (JSONArray) parent.get(arrayName);
                        if(nestedArray==null) {
                            nestedArray = new JSONArray();
                            nestedObject = new JSONObject();
                            nestedArray.add(nestedObject);
                        }
                        if(nestedArray.size()!=arrayIndex+1) {
                            nestedArray.add(new JSONObject());
                        }
                        nestedObject = (JSONObject) nestedArray.get(nestedArray.size()-1);
                        if(!st.hasMoreTokens())
                            parent.put(arrayName, processProperty(dataMap.get(key).toString()));
                        else
                            parent.put(arrayName, nestedArray);
                    } else {
                        nestedObject = (JSONObject) parent.get(nestedObjectKey);
                        if(nestedObject==null)
                            nestedObject = new JSONObject();
                        if(!st.hasMoreTokens())
                            parent.put(nestedObjectKey, processProperty(dataMap.get(key).toString()));
                        else
                            parent.put(nestedObjectKey, nestedObject);
                    }
                    parent = nestedObject;
                }
            } else {
                payload.put(key, processProperty(dataMap.get(key).toString()));
            }
        }
        return payload;
    }

    public static String createPayloadJSONObject(DataTable dataTable) throws AutomationException {
        String payload = "";
        if(dataTable==null || dataTable.isEmpty())
            return payload;
        Map<String, Object> dataMap = dataTable.asMap(String.class, String.class);
        Object payloadName = dataMap.get("payload");
        if(payloadName==null || payloadName.toString().isEmpty())
            return payload;
        return PayloadUtil.generatePayload(payloadName.toString(), dataMap);
    }

    public static void processHeaders(RequestSpecification requestSpecification, String headersString) {
        String[] headers = headersString.split(Constants.COMMA);
        for(String header : headers) {
            if(header.contains(":")) {
                String[] headerString = header.split(Constants.COLON);
                if(headerString.length>1)
                    requestSpecification.header(headerString[0].trim(), headerString[1].trim());
            }
        }
    }
}