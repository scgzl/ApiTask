package package1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class FirstGet {
    String url = "http://44.201.135.133:8000/api/spartans"; //  get Url
    String baseUrl = "http://44.201.135.133:8000";

    @BeforeEach // it will initialize the base url before each test
    public void init(){
        RestAssured.baseURI = "http://44.201.135.133:8000";
    }
    @Test
    public void test1(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .get(url);
        response.prettyPrint();
        System.out.println("response.statusCode() = " + response.statusCode());
        Assertions.assertEquals(200,response.statusCode());
    }

    //get headers
    @Test
    public void test2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .get(url);
        System.out.println(response.getHeaders().toString());
        /**
         * Content-Type=application/json
         * Transfer-Encoding=chunked
         * Date=Wed, 06 Jul 2022 01:09:07 GMT
         * Keep-Alive=timeout=60
         * Connection=keep-alive
         */
        Assertions.assertEquals("application/json",response.contentType());
        Assertions.assertEquals("keep-alive",response.getHeader("Connection"));
        Assertions.assertEquals("timeout=60",response.getHeader("Keep-Alive"));
        //getHeader is a method that gets header value from response
    }

    @Test
    public void test3(){
        Response response = RestAssured.given().accept(ContentType.XML)
                .get(baseUrl+"/api/spartans"); //for dynamic endpoint it is better to use in this way
        response.prettyPrint();
    }

    @Test //path  check the individuals properties by path
    public void test4(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .get("/api/spartans/411");

        response.prettyPrint();

        System.out.println(response.path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.path("phone").toString());

        Assertions.assertEquals("Michle",response.path("name"));
        Assertions.assertEquals("Male",response.path("gender"));

    }
    @Test
    public void test5(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().pathParam("id",411)
                .when().get("/api/spartans/{id}");

        response.prettyPrint();
    }
    @Test
    public void test6(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().queryParam("gender","Female")
                .when().get("/api/spartans/search");
      //  response.prettyPrint();

        //get
        Response responseMix = RestAssured.given().accept(ContentType.JSON)
                .when().queryParam("gender","Female")
                .when().queryParam("nameContains","j")
                .when().get("/api/spartans/search");
        responseMix.prettyPrint();
    }
    @Test // query params as map object
    public void test7(){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("gender","Female");
        queryParams.put("nameContains","j");

        Response response= RestAssured.given().accept(ContentType.JSON)
                .when().queryParams(queryParams)
                .when().get("/api/spartans/search");
        response.prettyPrint();
    }
    //negative testing
    @Test
    public void test8(){
        Response response= RestAssured.given().accept(ContentType.JSON)
                .get("/api/spartans/10000");
        Assertions.assertEquals(404,response.getStatusCode());
        response.prettyPrint();
    }
    @Test
    public void test9(){

        String newGuy = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"Ziya\",\n" +
                "  \"phone\": 8123124512\n" +
                "}";

        Response response= RestAssured.given().accept(ContentType.JSON) // response accept in json
                .and()
                .contentType(ContentType.JSON) // this is for body json newGuy
                .body(newGuy)
                .when().post("/api/spartans");
        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();

        Assertions.assertEquals("A Spartan is Born!",response.path("success"));
    }
    //update put
    @Test
    public void test10() {
        String updatedNewGuy = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"Kamil\",\n" +
                "  \"phone\": 8123124512\n" +
                "}";

        Response response= RestAssured.given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .body(updatedNewGuy)
                .pathParam("id",891)
                .when().put("/api/spartans/{id}");

        System.out.println(response.statusCode());
        response.prettyPrint();

    }
    //delete
    @Test
    public void test11(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .pathParam("id",891)
                .when().delete("/api/spartans/{id}");

        System.out.println("response.statusCode() = " + response.statusCode());
    }

}

