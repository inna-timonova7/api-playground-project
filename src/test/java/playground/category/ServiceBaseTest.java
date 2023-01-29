package playground.category;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseLogSpec;
import org.apache.http.HttpStatus;
import playground.BaseTest;
import playground.models.Data;
import playground.models.Service;

import java.io.IOException;

public class ServiceBaseTest extends BaseTest {

    public <T> T getListOfServices(Class<T> serviceSchema) {
        return publicApi()
                .get("/services")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(serviceSchema);
    }

    public <T> T getListOfServicesWithLimitParam(int limit, Class<T> serviceSchema) {
        return publicApi()
                .given()
                .queryParam("$limit", limit)
                .when()
                .get("/services")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(serviceSchema);
    }

    public <T> T getListOfServicesWithLimitAndSkipParam(int limit, int skip, Class<T> serviceSchema) {
        return publicApi()
                .given()
                .queryParam("$limit", limit)
                .queryParam("$skip", skip)
                .when()
                .get("/services")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(serviceSchema);
    }

    public <T> T getListOfServicesWithSkipParam(int skip, Class<T> serviceSchema) {
        return publicApi()
                .given()
                .queryParam("$skip", skip)
                .when()
                .get("/services")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(serviceSchema);
    }

    public <T> T getServiceId(long id, Class<T> serviceId) {
        return publicApi()
                .get("/services/{long}", id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(serviceId);
    }

    public <T> T createService(Service createServiceBody, Class<T> createdService) {
        return publicApi()
                .body(createServiceBody)
                .contentType("application/json")
                .post("/services")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(createdService);
    }

    public <T> T callUpdateServiceRequestAndExtractResponse(Service updateServiceBody, long id, Class<T> updatedService) {
        return publicApi()
                .body(updateServiceBody)
                .patch("/services/{long}", id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(updatedService);
    }

    public Response removeService(long id) {
        return (Response) publicApi()
                .delete("/services/{long}", id);
    }

    public ValidatableResponseLogSpec<ValidatableResponse, Response> get400ErrorByWrongIdGet404(long id) {
        return publicApi()
                .get("/services/{long}", id)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log();
    }

    public ValidatableResponseLogSpec<ValidatableResponse, Response> createServiceWithInvalidParametersGet400(Data createServiceBody) {
        return publicApi()
                .body(createServiceBody)
                .contentType("application/json")
                .post("/services")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .log();
    }

    public ValidatableResponseLogSpec<ValidatableResponse, Response> updateServiceWithInvalidIdAndGet404(Service updateServiceBody, long id) {
        return publicApi()
                .body(updateServiceBody)
                .patch("/services/{long}", id)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log();
    }

    public ValidatableResponseLogSpec<ValidatableResponse, Response> updateServiceWithNullBodyAndGet500(Service updateServiceBody, long id) throws IllegalArgumentException {
        try {
            return publicApi()
                    .body(updateServiceBody)
                    .patch("/services/{long}", id)
                    .then()
                    .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .log();

        } catch (IllegalArgumentException e) {
        } throw new IllegalArgumentException("object cannot be null");
    }

    public ValidatableResponseLogSpec<ValidatableResponse, Response> createServiceWithInvalidBodyAndGet500(Data createServiceBody) throws IllegalArgumentException {
        try {
            return publicApi()
                    .body(createServiceBody)
                    .contentType("application/json")
                    .post("/services")
                    .then()
                    .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .log();

        } catch (IllegalArgumentException e) {
        } throw new IllegalArgumentException("object cannot be null");
    }
}
