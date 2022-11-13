import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class TodoTest {

  static {
    RestAssured.baseURI = "http://localhost:3002/api/todoItems";
  }


  @Test
  public void testTodosValidResponse() {

    RequestSpecification httpRequest = RestAssured.given();
    Response response = httpRequest.request(Method.GET,
        "be76a375-b359-4a4f-94ba-5d6d59c06021");

    ResponseBody responseBody = response.body();
    assertEquals(response.getStatusCode(), 200);
    assertNotNull(responseBody);

  }


  @Test
  public void testTodosInvalidTodoId() {

    RequestSpecification httpRequest = RestAssured.given();
    Response response = httpRequest.request(Method.GET,
        "Random ID");
    assertEquals(response.getStatusCode(), 404);
  }


  @Test
  public void testAddTodoWithValidRequestBody() {

    RequestSpecification httpRequest = RestAssured.given()
        .header("Content-type", "application/json");
    JSONObject requestBody = new JSONObject();
    requestBody.put("description", "New TODO");

    Response response = httpRequest.body(requestBody.toString()).request(Method.POST);

    ResponseBody responseBody = response.body();
    assertEquals(response.getStatusCode(), 201);
    assertNotNull(responseBody);

  }

  @Test
  public void testAddTodoWithInvalidRequestBody() {

    RequestSpecification httpRequest = RestAssured.given()
        .header("Content-type", "application/json");
    JSONObject requestBody = new JSONObject();
    requestBody.put("randomProp", "New TODO");

    Response response = httpRequest.body(requestBody.toString()).request(Method.POST);

    String responseBody = response.body().asString();
    assertEquals(response.getStatusCode(), 400);
    System.out.println(responseBody);
    assertTrue(responseBody.contains("One or more validation errors occurred."));
  }

  @Test
  public void testAddTodoWithDupilcatedData() {

    RequestSpecification httpRequest = RestAssured.given()
        .header("Content-type", "application/json");
    JSONObject requestBody = new JSONObject();
    requestBody.put("description", "New TODO");

    Response response = httpRequest.body(requestBody.toString()).request(Method.POST);

    String responseBody = response.body().asString();
    assertEquals(response.getStatusCode(), 409);
    assertEquals(responseBody, "A todo item with description already exists");

  }


  @Test
  public void PutTodos() {

    RequestSpecification httpRequest = RestAssured.given()
        .header("Content-type", "application/json");
    JSONObject requestBody = new JSONObject();

    requestBody.put("id", "be76a375-b359-4a4f-94ba-5d6d59c06021");
    requestBody.put("description", "Raja sekhar");
    requestBody.put("isCompleted", false);

    Response response = httpRequest.body(requestBody.toString()).request(Method.PUT,
        "be76a375-b359-4a4f-94ba-5d6d59c06021");
    assertEquals(response.getStatusCode(), 204);

  }
}
