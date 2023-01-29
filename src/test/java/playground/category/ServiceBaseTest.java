package playground.category;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import playground.BaseTest;
import playground.models.Service;

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
                .contentType("application/json")
                .patch("/services/{long}", id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(updatedService);
    }

    public Response removeService(long id) {
        return (Response) publicApi()
                .contentType("application/json")
                .delete("/services/{long}", id);
    }
}
