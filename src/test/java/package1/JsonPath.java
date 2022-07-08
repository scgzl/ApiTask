package package1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.TestBase;

public class JsonPath extends TestBase {

    @DisplayName("Using JsonPath")
    @Test
    public void test1(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/api/spartans/411");
        response.prettyPrint();

        response.jsonPath();

        String name = response.jsonPath().getString("name");
        Long phone = response.jsonPath().getLong("phone");

        System.out.println("name = " + name);
        System.out.println("phone = " + phone);

    }

}
