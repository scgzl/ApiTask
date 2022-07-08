package package1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchers {
    @BeforeEach
    public void init(){
        RestAssured.baseURI="http://44.201.135.133:8000";
    }
    @Test
    public void test1(){
        assertThat(5+5,is(10));
        assertThat(5+5,equalTo(10));
        assertThat(5+5,not(11));
    }
    @Test
    public void test2(){
        String test = "api";

        assertThat(test,is("api"));
        assertThat(test,containsString("api"));

        List<Integer> list  = Arrays.asList(1,5,4,23,6,7,6,87);

        assertThat(list,hasItems(1,5));
    }
    @Test
    public void test3(){
        given().accept(ContentType.JSON)
                .and()
                .pathParams("id",411)
                .when()
                .get("/api/spartans/{id}")
                .then().assertThat()
                .body("id",is(411),"name",is("Michle"));
    }

}
