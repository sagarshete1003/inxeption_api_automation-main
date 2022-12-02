package com.api.automation.api;

import com.api.automation.util.ReportLogger;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

public class ApiWorkflow {
    public static ReportLogger reportLogger = ReportLogger.getInstance();

    public static Response callGetService(String endpoint, String headers, String token, String payload) {
        reportLogger.info("Endpoint URL: "+endpoint);
        reportLogger.info("--------------------------------------------------------------------------------------");
        reportLogger.info("Payload: "+payload);

        // Without this the request fails so adding it here, not sure what's up with that
        //RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Accept","application/json");
        if(token!=null && !token.isEmpty())
            requestSpecification.header("Authorization", "Bearer "+token);
        if(headers!=null && !headers.isEmpty())
            ApiUtil.processHeaders(requestSpecification,headers);
        if(payload!=null)
            requestSpecification.when().body(payload);
        Response response = requestSpecification.get(endpoint).thenReturn();
        reportLogger.info("Response: "+response.getBody().asString());
        return response;
    }

    public static Response callDeleteService(String endpoint, String headers, String token, String payload) {
        reportLogger.info("Endpoint URL: "+endpoint);
        reportLogger.info("--------------------------------------------------------------------------------------");
        reportLogger.info("Payload: "+payload);

        //RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Accept","application/json");
        if(token!=null && !token.isEmpty())
            requestSpecification.header("Authorization", "Bearer "+token);
        if(headers!=null && !headers.isEmpty())
            ApiUtil.processHeaders(requestSpecification,headers);
        if(payload!=null)
            requestSpecification.when().body(payload);
        Response response = requestSpecification.delete(endpoint).thenReturn();
        reportLogger.info("Response: "+response.getBody().asString());
        return response;
    }

    public static Response callPostService(String endpoint, String headers, String token, String payload) {
        reportLogger.info("Endpoint URL: "+endpoint);
        reportLogger.info("--------------------------------------------------------------------------------------");
        reportLogger.info("Payload: "+payload);
        //RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        //String payloadStr = "{\"query\":\"mutation {customerLogin (email: \\\"sagar.shete@ext.inxeption.com\\\",passphrase: \\\"S@gar111\\\") {token, customer, firstName, lastName, phone}}\",\"variables\":{}}";
        //String payloadStr = "{\"query\":\"{\\n\\t\\t\\tsessionChannelProducts(first: 100 skip: 0 where:{ AND: [{ requiresSubscription:false }, { channel: { code: \\\"inxeptionenergysilver\\\" } },{channelProductPageItems_some: {channelProductPage: {code: \\\"default\\\"}}}]} orderBy: name_ASC) {\\n\\t\\t\\t\\t\\n\\tname\\n\\thandle\\n\\tid\\n\\tprice\\n\\tallowsQuotes\\n\\thidePrice\\n\\toutOfStock\\n\\tproduct { \\n\\tid\\n\\thandle\\n\\ttype\\n\\tname\\n\\tbaseFixtureCode\\n\\tprice\\n\\tshippingMethod\\n\\tproductVariants (orderBy: order_ASC) {\\n\\t\\tid\\n\\t\\tname\\n\\t\\tcode\\n\\t\\torder\\n\\t\\tproductVariantOptions (orderBy: order_ASC) {\\n\\t\\t\\tid\\n\\t\\t\\tcode\\n\\t\\t\\tname\\n\\t\\t\\torder\\n\\t\\t\\tcogChange\\n\\t\\t\\tinputRequired\\n\\t\\t\\tinputType\\n\\t\\t\\tinputDescription\\n\\t\\t}\\n\\t}\\n }\\n\\tchannelProductImgs(orderBy: order_ASC) {\\n\\t\\tfile { \\n\\tid\\n\\tname\\n\\turl\\n\\tsize\\n\\tcontentType\\n }\\n\\t}\\n\\tchannel {\\n\\t\\tcode\\n\\t\\tname\\n\\t\\tchannelProductPages {\\n\\t\\t\\tcode\\n\\t\\t\\tname\\n\\t\\t}\\n\\t}\\n\\torderLineItems(\\n\\t\\torderBy: createdAt_DESC\\n      \\tfirst: 1\\n\\t\\twhere: {\\n\\t\\torderForm: {\\n\\t\\t\\tstatus: Completed\\n\\t\\t\\tchannel: {\\n\\t\\t\\t\\tchannelUsers_some: { customer: { id: \\\"cl33ca9054uwl0818b3f9dhcv\\\" } }\\n\\t\\t\\t}\\n\\t\\t}\\n\\t\\t}\\n\\t){\\n\\t\\tid\\n\\t\\tname\\n\\t\\tcreatedAt\\n\\t}\\n\\tchannelProductVariants(orderBy: order_ASC) {\\n\\t\\tid\\n\\t\\tname\\n\\t\\tcode\\n\\t\\torder\\n\\t\\tproductVariant {\\n\\t\\t\\tid\\n\\t\\t\\tname\\n\\t\\t\\tcode\\n\\t\\t\\torder\\n\\t\\t}\\n\\t\\tchannelProductVariantOptions(orderBy: order_ASC) {\\n\\t\\t\\tid\\n\\t\\t\\tcode\\n\\t\\t\\tname\\n\\t\\t\\torder\\n\\t\\t\\toriginalPriceChange\\n\\t\\t\\tpriceChange\\n\\t\\t\\tinputRequired\\n\\t\\t\\tinputType\\n\\t\\t\\tinputDescription\\n\\t\\t\\tproductVariantOption {\\n\\t\\t\\t\\tid\\n\\t\\t\\t\\tcode\\n\\t\\t\\t\\tname\\n\\t\\t\\t\\torder\\n\\t\\t\\t\\tcogChange\\n\\t\\t\\t\\tshipWeightChange\\n\\t\\t\\t\\tshipHeightChange\\n\\t\\t\\t\\tshipWidthChange\\n\\t\\t\\t\\tshipLengthChange\\n\\t\\t\\t\\tinputRequired\\n\\t\\t\\t\\tinputType\\n\\t\\t\\t\\tinputDescription\\n\\t\\t\\t\\tsku\\n\\t\\t\\t}\\n\\t\\t}\\n\\t}\\n\\n\\t\\t\\t}\\n\\t\\t\\tsessionChannelProductsConnection ( where:{ AND: [{ requiresSubscription:false }, { channel: { code: \\\"inxeptionenergysilver\\\" } },{channelProductPageItems_some: {channelProductPage: {code: \\\"default\\\"}}}]} ) {\\n\\t\\t\\t\\taggregate {\\n\\t\\t\\t\\t\\tcount\\n\\t\\t\\t\\t}\\n\\t\\t\\t}\\n\\t\\t\\tallChannelProductsCount: sessionChannelProductsConnection {\\n\\t\\t\\t\\taggregate {\\n\\t\\t\\t\\t\\tcount\\n\\t\\t\\t\\t}\\n\\t\\t\\t}\\n\\t\\t\\tchannelProductPages(where: {channel: {code: \\\"inxeptionenergysilver\\\"}, type: Category}, orderBy: order_ASC) {\\n\\t\\t\\t\\tname\\n\\t\\t\\t\\tcode\\n\\t\\t\\t}\\n\\t\\t}\",\"variables\":{}}";
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Accept","application/json");
        if(token!=null && !token.isEmpty())
            requestSpecification.header("Authorization", "Bearer "+token);
        if(headers!=null && !headers.isEmpty())
            ApiUtil.processHeaders(requestSpecification,headers);
        Response response = requestSpecification.when().body(payload).post(endpoint).then().log().all().extract().response();
        reportLogger.info("Response: "+response.getBody().asString());
        return response;
    }

    public static Response callPatchService(String endpoint, String headers, String token, String payload) {
        reportLogger.info("Endpoint URL: "+endpoint);
        reportLogger.info("--------------------------------------------------------------------------------------");
        reportLogger.info("Payload: "+payload);
        //RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Accept","application/json");
        if(token!=null && !token.isEmpty())
            requestSpecification.header("Authorization", "Bearer "+token);
        if(headers!=null && !headers.isEmpty())
            ApiUtil.processHeaders(requestSpecification,headers);
        Response response = requestSpecification.when().body(payload).request().patch(endpoint).then().log().all().extract().response();
        reportLogger.info("Response: "+response.getBody().asString());
        return response;
    }

    public static Response callPutService(String endpoint, String headers, String token, String payload) {
        reportLogger.info("Endpoint URL: "+endpoint);
        reportLogger.info("--------------------------------------------------------------------------------------");
        reportLogger.info("Payload: "+payload);
        //RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Accept","application/json");
        if(token!=null && !token.isEmpty())
            requestSpecification.header("Authorization", "Bearer "+token);
        if(headers!=null && !headers.isEmpty())
            ApiUtil.processHeaders(requestSpecification,headers);
        Response response = requestSpecification.when().body(payload.toString()).request().patch(endpoint).then().log().all().extract().response();
        reportLogger.info("Response: "+response.getBody().asString());
        return response;
    }
}
