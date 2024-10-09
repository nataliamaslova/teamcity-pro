package org.example.teamcitypro.api;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.example.teamcitypro.api.models.User;
import org.example.teamcitypro.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {
    @Test
    public static void buildConfigurationTest() {
        var user = User.builder()
                .userName("admin")
                .password("admin")
                .build();
        var token = RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        System.out.println("token");
    }
}
