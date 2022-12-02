package glue;

import com.api.automation.api.ApiUtil;
import com.api.automation.api.ApiWorkflow;
import com.api.automation.exception.AutomationException;
import com.api.automation.hooks.CustomHooks;
import com.api.automation.util.DataCache;
import com.api.automation.util.ReportLogger;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.StringTokenizer;

public class ApiSteps {
    ReportLogger reportLogger = ReportLogger.getInstance();
    private static String EMPTY_STRING="";

    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\"$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\"$")
    public void callGetServiceAndRecordResponse(String method, String endpoint) throws ParseException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s", method,endpointStr));
        Response response = ApiWorkflow.callGetService(endpointStr, null, null, null);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }

    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\"$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\"$")
    public void callServiceWithHeadersAndRecordResponse(String method, String endpoint, String headers) throws ParseException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s", method,endpointStr));
        Response response = null;
        if (method.equalsIgnoreCase("GET"))
            response = ApiWorkflow.callGetService(endpointStr, headers, null, null);
        else if (method.equalsIgnoreCase("DELETE"))
            response = ApiWorkflow.callDeleteService(endpointStr, headers, null, null);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }


    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" and payload:$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" and payload:$")
    public void callServiceAndRecordResponse(String method, String endpoint, DataTable dataTable) throws ParseException, AutomationException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s", method,endpointStr));
        String payload = ApiUtil.createPayloadJSONObject(dataTable);
        CustomHooks.CURRENT_STEP_MESSAGE.set(payload);
        Response response = null;
        if (method.equalsIgnoreCase("POST"))
            response = ApiWorkflow.callPostService(endpointStr, null, null, payload);
        else if (method.equalsIgnoreCase("GET"))
            response = ApiWorkflow.callGetService(endpointStr, null, null, payload);
        else if (method.equalsIgnoreCase("DELETE"))
            response = ApiWorkflow.callDeleteService(endpointStr, null, null, payload);
        else if (method.equalsIgnoreCase("PATCH"))
            response = ApiWorkflow.callPatchService(endpointStr, null, null, payload);
        else if (method.equalsIgnoreCase("PUT"))
            response = ApiWorkflow.callPutService(endpointStr, null, null, payload);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }


    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\" and payload:$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\" and payload:$")
    public void callServiceWithHeaderAndRecordResponse(String method, String endpoint, String headers, DataTable dataTable) throws ParseException, AutomationException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s", method,endpointStr));
        String payload = ApiUtil.createPayloadJSONObject(dataTable);
        CustomHooks.CURRENT_STEP_MESSAGE.set(payload);
        Response response = null;
        if (method.equalsIgnoreCase("POST"))
            response = ApiWorkflow.callPostService(endpointStr, headers, null, payload);
        else if (method.equalsIgnoreCase("GET"))
            response = ApiWorkflow.callGetService(endpointStr, headers, null, payload);
        else if (method.equalsIgnoreCase("DELETE"))
            response = ApiWorkflow.callDeleteService(endpointStr, headers, null, payload);
        else if (method.equalsIgnoreCase("PATCH"))
            response = ApiWorkflow.callPatchService(endpointStr, headers, null, payload);
        else if (method.equalsIgnoreCase("PUT"))
            response = ApiWorkflow.callPutService(endpointStr, headers, null, payload);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }

    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\" and token: \"([^\"]*)\" and payload:$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with headers: \"([^\"]*)\" and token: \"([^\"]*)\" and payload:$")
    public void callServiceWithHeaderAndApiKeyRecordResponse(String method, String endpoint, String headers, String token, DataTable dataTable) throws ParseException, AutomationException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s token: *****", method,endpointStr));
        String payload = ApiUtil.createPayloadJSONObject(dataTable);
        CustomHooks.CURRENT_STEP_MESSAGE.set(payload);
        token = ApiUtil.processProperty(token).toString();
        Response response = null;
        if (method.equalsIgnoreCase("POST"))
            response = ApiWorkflow.callPostService(endpointStr, headers, token, payload);
        else if (method.equalsIgnoreCase("GET"))
            response = ApiWorkflow.callGetService(endpointStr, headers, token, payload);
        else if (method.equalsIgnoreCase("DELETE"))
            response = ApiWorkflow.callDeleteService(endpointStr, headers, token, payload);
        else if (method.equalsIgnoreCase("PATCH"))
            response = ApiWorkflow.callPatchService(endpointStr, headers, token, payload);
        else if (method.equalsIgnoreCase("PUT"))
            response = ApiWorkflow.callPutService(endpointStr, headers, token, payload);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }

    @Then("^execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with token: \"([^\"]*)\" and payload:$")
    @When("^user execute HTTP request method: \"([^\"]*)\" endpoint: \"([^\"]*)\" with token: \"([^\"]*)\" and payload:$")
    public void callServiceWithHeaderAndApiKeyRecordResponse(String method, String endpoint, String token, DataTable dataTable) throws ParseException, AutomationException {
        String endpointStr = ApiUtil.processProperty(endpoint).toString();
        reportLogger.addStep(String.format("Execute HTTP request method: %s endpoint: %s token: *****", method,endpointStr));
        String payload = ApiUtil.createPayloadJSONObject(dataTable);
        CustomHooks.CURRENT_STEP_MESSAGE.set(payload);
        token = ApiUtil.processProperty(token).toString();
        Response response = null;
        if (method.equalsIgnoreCase("POST"))
            response = ApiWorkflow.callPostService(endpointStr, null, token, payload);
        else if (method.equalsIgnoreCase("GET"))
            response = ApiWorkflow.callGetService(endpointStr, null, token, payload);
        else if (method.equalsIgnoreCase("DELETE"))
            response = ApiWorkflow.callDeleteService(endpointStr, null, token, payload);
        else if (method.equalsIgnoreCase("PATCH"))
            response = ApiWorkflow.callPatchService(endpointStr, null, token, payload);
        else if (method.equalsIgnoreCase("PUT"))
            response = ApiWorkflow.callPutService(endpointStr, null, token, payload);
        System.out.print(response != null ? response.getBody() : null);
        DataCache.saveResponse(endpoint, response);
    }

    @Then("^verify response from last request to \"([^\"]*)\" includes:$")
    @When("^user verify response from last request to \"([^\"]*)\" includes:$")
    public void verifyResponseData(String responseReference, DataTable dataTable) throws AutomationException {
        reportLogger.addStep(String.format("User verify %s response data \n"+dataTable.asMap(String.class,String.class).toString(), responseReference));
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        Response response = DataCache.readResponse(responseReference);
        CustomHooks.CURRENT_STEP_MESSAGE.set(response.asString());
        for (String key : dataMap.keySet()) {
            String formattedKey = "";
            if(key.contains(":")) {
                StringTokenizer st = new StringTokenizer(key,".");
                while (st.hasMoreTokens()) {
                    String nestedObjectKey = st.nextToken();
                    if(nestedObjectKey.contains(":") && st.hasMoreTokens())
                        formattedKey = formattedKey + "'"+nestedObjectKey+"'"+".";
                    else if(nestedObjectKey.contains(":"))
                        formattedKey = formattedKey + "'"+nestedObjectKey+"'";
                    else if(st.hasMoreTokens())
                        formattedKey = formattedKey+nestedObjectKey+".";
                    else
                        formattedKey = formattedKey+nestedObjectKey;

                }
            } else {
                formattedKey = key;
            }
            if (key.equalsIgnoreCase("statusCode")) {
                ApiUtil.verifyStatusCode(response, Integer.parseInt(ApiUtil.processProperty(dataMap.get(key)).toString()));
            } else if (key.equalsIgnoreCase("responseJson") || key.equalsIgnoreCase("responseBody")
                    || key.equalsIgnoreCase("response") || key.equalsIgnoreCase("body")) {
                String actualValue = response.getBody().asString();
                String expectedValue = dataMap.get(key);
                System.out.println("actualValue::::"+actualValue);
                System.out.println("expectedValue::::"+expectedValue);
                if(!actualValue.equalsIgnoreCase(expectedValue))
                    throw new AutomationException(key+" ===> Actual Value is '"+actualValue+"' But Expected Value is '"+expectedValue+"'");
            } else if (dataMap.get(key).startsWith("^")) {
                ApiUtil.verifyJsonValue(response, formattedKey, Integer.parseInt(ApiUtil.processProperty(dataMap.get(key)).toString()));
            } else if (dataMap.get(key).startsWith("*")) {
                ApiUtil.verifyJsonValues(response, formattedKey, Boolean.parseBoolean(ApiUtil.processProperty(dataMap.get(key)).toString()));
            } else if(dataMap.get(key).equalsIgnoreCase("[]")){
                ApiUtil.verifyJsonValueEmptyArray(response, formattedKey);
            }else if(dataMap.get(key).startsWith("[") && dataMap.get(key).endsWith("]")){
                 ApiUtil.verifyJsonValues(response, formattedKey, (String[]) ApiUtil.processProperty(dataMap.get(key)));
            } else if(dataMap.get(key).equalsIgnoreCase("NOT NULL")){
                ApiUtil.verifyJsonValueNotNull(response, formattedKey);
            } else if(dataMap.get(key).equalsIgnoreCase("NULL")){
                ApiUtil.verifyJsonValueIsNull(response, formattedKey);
            } else if(dataMap.get(key).equalsIgnoreCase("NOT EMPTY")){
                ApiUtil.verifyJsonValueNotEmpty(response, formattedKey);
            } else if(dataMap.get(key).equalsIgnoreCase("EMPTY")){
                ApiUtil.verifyJsonValueEmpty(response, formattedKey);
            } else {
                ApiUtil.verifyJsonValue(response, formattedKey, ApiUtil.processProperty(dataMap.get(key)).toString());
            }
        }

    }

    @Then("^save \"([^\"]*)\" from the response from \"([^\"]*)\" into the variable \"([^\"]*)\"$")
    @When("^user save \"([^\"]*)\" from the response from \"([^\"]*)\" into the variable \"([^\"]*)\"$")
    public void saveResponseValueAsVariable(String jsonPath, String responseReference, String referenceVar) {
        reportLogger.addStep(String.format("Save %s from the response from %s into the variable %s", jsonPath,responseReference,referenceVar));
        Response response = DataCache.readResponse(responseReference);
        Object actualValue=response.getBody().jsonPath().get(jsonPath);
        System.out.println(jsonPath+" in Response:::: "+actualValue);
        DataCache.saveVariable(referenceVar, actualValue.toString());
        CustomHooks.CURRENT_STEP_MESSAGE.set(String.format("Captured %s value from %s response into %s as %s", jsonPath ,responseReference ,referenceVar ,actualValue));
    }

    @Then("^assign \"([^\"]*)\" into \"([^\"]*)\"$")
    @When("^user assign \"([^\"]*)\" into \"([^\"]*)\"$")
    public void createVariable(String uniqueKey, String referenceVar) {
        reportLogger.addStep(String.format("User assign %s into %s", uniqueKey,referenceVar));
        String processedValue = ApiUtil.processProperty(uniqueKey).toString();
        DataCache.saveVariable(referenceVar, processedValue);
    }

    @Then("^sum integer array \"([^\"]*)\" into \"([^\"]*)\"$")
    @When("^user sum integer array \"([^\"]*)\" into \"([^\"]*)\"$")
    public void getSum(String values, String referenceVar) {
        reportLogger.addStep(String.format("User get sum of %s into %s", values,referenceVar));
        Integer sum = 0;
        if(values.startsWith("[") && values.endsWith("]") && values.contains(",")) {
            String[] valArray = values.substring(1, values.length()-1).split(",");
            for(String val: valArray) {
                sum = sum + Integer.parseInt(ApiUtil.processProperty(val.trim()).toString());
            }
        }
        DataCache.saveVariable(referenceVar, sum.toString());
    }

    @Then("^verify \"([^\"]*)\" is equal to \"([^\"]*)\"$")
    @When("^user verify \"([^\"]*)\" is equal to \"([^\"]*)\"$")
    public void verifyEqual(String actual, String expected) throws AutomationException {
        reportLogger.addStep(String.format("User verify %s into %s", actual,expected));
        String actualStr = ApiUtil.processProperty(actual.trim()).toString();
        String expectedStr = ApiUtil.processProperty(expected.trim()).toString();
        System.out.println("actualValue::::"+actualStr);
        System.out.println("expectedValue::::"+expectedStr);
        ApiUtil.verifyEqual(actualStr, expectedStr);
    }

    @When("^user wait \"([^\"]*)\" seconds$")
    @Then("^wait \"([^\"]*)\" seconds$")
    public void sleepNSeconds(String seconds) throws InterruptedException {
        int numSeconds = Integer.parseInt(seconds);
        Thread.sleep(numSeconds * 1000);
    }

}


