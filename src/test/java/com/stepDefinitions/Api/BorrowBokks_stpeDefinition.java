package com.stepDefinitions.Api;

import com.POJO.StudentUserPayLoad;
import com.github.fge.jsonschema.library.Library;
import com.utils.ConfigurationReader;
import com.utils.JavaFakerUtil;
import com.utils.LibraryUtils;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class BorrowBokks_stpeDefinition {

    private  String token= LibraryUtils.getTokenDefault_Env();
    private Response response;
    private String id;
    private static Response studentResponse;

   static StudentUserPayLoad studentUserPayLoad;

    @Given("Create a new student.")
    public void create_a_new_student() {
     studentUserPayLoad=JavaFakerUtil.createRandomStudentPOJO();

        System.out.println(studentUserPayLoad);

      response=   given().contentType(ContentType.JSON).
                 header("x-library-token",token)
                 .body(studentUserPayLoad).
         when().
                 post("/add_user");



    }

    @When("Save id of that student in class variable")
    public void save_id_of_that_student_in_class_variable() {
     id=  response.then()
               .log().body()
               .extract().body().path("user_id");
        System.out.println(id);

    }
    @Then("Get token for that student and save it class variable.")
    public void get_token_for_that_student_and_save_it_class_variable() {
//
        studentResponse = given().
                formParam("email", studentUserPayLoad.getEmail()).
                formParam("password", studentUserPayLoad.getPassword()).
                contentType(ContentType.URLENC).
                when().
                post("login").prettyPeek();

        studentUserPayLoad.setToken( studentResponse.body().jsonPath().getString("token"));

    }


}
