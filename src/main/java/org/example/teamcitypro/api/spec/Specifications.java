package org.example.teamcitypro.api.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.teamcitypro.api.config.Config;
import org.example.teamcitypro.api.models.User;

public class Specifications {
    private static RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public static RequestSpecification superUserAuth() {
        var requestBuilder = reqBuilder();
        String uri = "http://%s:%s@%s/httpAuth".formatted("", Config.getProperty("superUserToken"), Config.getProperty("host"));
        requestBuilder.setBaseUri(uri);
        return requestBuilder.build();
    }

    public  static RequestSpecification unAuthSpec() {
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }
    public static RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://%s:%s@%s".formatted(user.getUsername(), user.getPassword(), Config.getProperty("host")));
        return requestBuilder.build();
    }
}
