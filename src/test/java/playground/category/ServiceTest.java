package playground.category;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import playground.models.ServiceSchema;

import static org.junit.Assert.assertNotNull;

public class ServiceTest extends ServiceBaseTest {

    @Test
    public void whenGetServicesWithoutParams() {
        ServiceSchema services = getListOfServices(ServiceSchema.class);
        assertNotNull(services);
    }
}
