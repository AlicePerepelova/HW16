package specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.filter.log.RequestLoggingFilter.with;
import static io.restassured.http.ContentType.JSON;

public class SuccessCreateSpec {
  public static RequestSpecification defaultRequestSpec  =with()
          .filter(withCustomTemplates())
          .log().all()
          .contentType(JSON);
  public static ResponseSpecification createResponseSpec200=new ResponseSpecBuilder()
    .expectStatusCode(200)
    .log(STATUS)
    .log(BODY)
    .build();
  public static ResponseSpecification createResponseSpec201=new ResponseSpecBuilder()
    .expectStatusCode(201)
    .log(STATUS)
    .log(BODY)
    .build();
  public static ResponseSpecification createResponseSpec204=new ResponseSpecBuilder()
    .expectStatusCode(204)
    .log(STATUS)
    .log(BODY)
    .build();
  public static ResponseSpecification createResponseSpec400=new ResponseSpecBuilder()
    .expectStatusCode(400)
    .log(STATUS)
    .log(BODY)
    .build();
}
