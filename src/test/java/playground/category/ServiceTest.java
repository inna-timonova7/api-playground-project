package playground.category;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import playground.models.Data;
import playground.models.Service;
import playground.models.ServiceSchema;

import java.util.ArrayList;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.*;

public class ServiceTest extends ServiceBaseTest {

    private final ServiceSchema serviceSchema = new ServiceSchema();

    private final Service createServiceBody = new Service("NewService");
    private final Service createServiceBody2 = new Service("KamikazeService");

    private final Data createServiceBody3 = new Data();
    private final Service updateServiceBody = new Service("UpdatedService");
    private final int limit1 = 3;
    private final int limit2 = 0;

    private final int skip1 = 2;
    private final int skip2 = serviceSchema.getTotal();

    @Test
    public void whenGetServicesWithoutParams() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        assertNotNull(services);
        Assert.assertTrue(String.valueOf(new ArrayList<>(services.getData()).subList(1, 10)), true);
    }

    @Test
    public void whenGetServicesWithLimitParam() {
        ServiceSchema services = getListOfServicesWithLimitParam(limit1, ServiceSchema.class);
        assertNotNull(services);
        Assert.assertTrue(String.valueOf(new ArrayList<>(services.getData()).subList(1, 3)), true);
    }

    @Test
    public void whenGetServicesWithSkipParam() {
        ServiceSchema services = getListOfServicesWithSkipParam(skip1, ServiceSchema.class);
        assertNotNull(services);
        Assert.assertTrue(String.valueOf(new ArrayList<>(services.getData()).subList(3, 10)), true);
    }

    @Test
    public void whenGetServicesWithLimitAndSkipParams() {
        ServiceSchema services = getListOfServicesWithLimitAndSkipParam(limit2, skip2, ServiceSchema.class);
        assertNotNull(services);
        Assert.assertTrue(String.valueOf(services.getData().isEmpty()), true);
    }

    @Test
    public void whenGetServiceById() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        Data serviceById = getServiceId(services.getData().get(0).getId(), Data.class);
        Assert.assertEquals(services.getData().get(0).getId(), 1);
    }

    @Test
    public void whenGetServiceByWrongId() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        get400ErrorByWrongIdGet404(0);
        Assert.assertTrue(String.valueOf(HttpStatus.SC_NOT_FOUND), true);
    }

    @Test
    public void whenCreateService() {
        Data createdService = createService(createServiceBody, Data.class);
        assertNotNull(createdService);
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        Assert.assertEquals(createdService.getName(), "NewService");
        Assert.assertTrue(String.valueOf(services.getData().stream().filter(data -> Objects.equals(data.getName(), "NewService"))), true);
        removeService(createdService.getId());
    }

    @Test
    public void whenCreateServiceWithInvalidBodyAndGet500() {
        assertThatThrownBy(() -> createServiceWithInvalidBodyAndGet500(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("object cannot be null");
    }

    @Test
    public void whenCreateServiceWithInvalidParameters2() {
        createServiceWithInvalidParametersGet400(createServiceBody3);
        Assert.assertTrue(String.valueOf(HttpStatus.SC_BAD_REQUEST), true);
    }

    @Test
    public void whenUpdateService() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        Data serviceById = getServiceId(services.getData().get(0).getId(), Data.class);
        Data updatedService = callUpdateServiceRequestAndExtractResponse(updateServiceBody, services.getData().get(0).getId(), Data.class);
        assertNotNull(updatedService);
        Assert.assertEquals(updatedService.getName(), "UpdatedService");
        Assert.assertEquals(updatedService.getId(), services.getData().get(0).getId());
    }

    @Test
    public void whenUpdateServiceWithInvalidId() {
        updateServiceWithInvalidIdAndGet404(updateServiceBody, 10000);
        Assert.assertTrue(String.valueOf(HttpStatus.SC_NOT_FOUND), true);
    }

    @Test
    public void whenUpdateServiceWithNullBody() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        assertThatThrownBy(() -> updateServiceWithNullBodyAndGet500(null, services.getData().get(0).getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("object cannot be null");
    }

    @Test
    public void whenRemoveService() {
        Data createdService = createService(createServiceBody2, Data.class);
        assertNotNull(createdService);
        createdService.getId();
        removeService(createdService.getId());
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        Assert.assertFalse(String.valueOf(createdService.getId()), false);
        Assert.assertFalse(String.valueOf(services.getData().stream().filter(data -> Objects.equals(data.getName(), "KamikazeService"))), false);
    }

    @Test
    public void whenRemoveAlreadyRemovedService() {
        Data createdService = createService(createServiceBody2, Data.class);
        assertNotNull(createdService);
        createdService.getId();
        removeService(createdService.getId());
        removeService(createdService.getId())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
