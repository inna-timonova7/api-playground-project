package playground.category;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import playground.BaseTest;
import playground.models.Service;

import java.util.*;

public class ServiceBaseTest extends BaseTest {

    private final Service createServiceBody = new Service("NewService");
    private final Service updateServiceBody = new Service("UpdatedService");
    private final int limit = 3;

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
                .queryParam(String.valueOf(limit))
                .get("/services")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(serviceSchema);
    }

    public <T> T getServiceId(UUID id, Class<T> serviceId) {
        return publicApi()
                .get("/services/{UUID}", id)
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

    public Service callUpdateServiceRequestAndExtractResponse (Service updateServiceBody, Class<Service> updatedService, UUID id) {
        return publicApi()
                .body(updateServiceBody)
                .contentType("application/json")
                .patch("/services/{UUID}", id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(updatedService);
    }

    public Response removeService(UUID id) {
        return (Response) publicApi()
                .contentType("application/json")
                .delete("/services/{UUID}", id);
    }
}
