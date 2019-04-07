package com.aba.rover.boundary.v1;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiEngineTest {

    @Test
    @Order(1)
    public void shouldGetNoneDirection() {

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().get("/api/v1/engine/move")
                .then()
                .statusCode(200)
                .body(is("\"NONE\""));
    }

    @Test
    @Order(2)
    public void shouldNotMoveBecauseNoBattery() {

        given()
                .contentType(ContentType.JSON)
                .body("{\"direction\":\"FORWARD\"}")
                .post("/api/v1/engine/move")
                .then()
                .statusCode(204);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().get("/api/v1/engine/move")
                .then()
                .statusCode(200)
                .body(is("\"NONE\""));
    }

    @Test
    @Order(3)
    public void shouldNotGetBattery() {

        given()
                .when().get("/api/v1/engine/battery/remaining")
                .then()
                .statusCode(200)
                .body(is("0.0 %"));
    }

    @Test
    @Order(4)
    public void shouldChargeBattery() {

        given()
                .contentType(ContentType.JSON)
                .post("/api/v1/engine/battery/charge")
                .then()
                .statusCode(204);

        given()
                .when().get("/api/v1/engine/battery/remaining")
                .then()
                .statusCode(200)
                .body(is("100.0 %"));
    }

    @Test
    @Order(5)
    public void shouldMoveForward() {

        given()
                .contentType(ContentType.JSON)
                .body("{\"direction\":\"FORWARD\"}")
                .post("/api/v1/engine/move")
                .then()
                .statusCode(204);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().get("/api/v1/engine/move")
                .then()
                .statusCode(200)
                .body(is("\"FORWARD\""));
    }

}
