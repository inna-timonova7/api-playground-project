package playground;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    protected static final String PLATFORM_URL = System.getProperty(
            "localhost:3030.target.platform.url",
            "localhost:3030");

    public BaseTest() {
        RestAssured.config = RestAssuredConfig
                .config()
                .objectMapperConfig(
                        new ObjectMapperConfig().jackson2ObjectMapperFactory(
                                (cls, charset) -> {
                                    SimpleModule module = new SimpleModule();
                                    return new ObjectMapper()
                                            .findAndRegisterModules()
                                            .registerModule(module)
                                            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
                                                    false)
                                            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
                                                    false);
                                }
                        ));
        RestAssured.replaceFiltersWith(
                new RequestLoggingFilter(LogDetail.METHOD),
                new RequestLoggingFilter(LogDetail.URI),
                new RequestLoggingFilter(LogDetail.HEADERS),
                new RequestLoggingFilter(LogDetail.BODY),
                new ResponseLoggingFilter(LogDetail.STATUS),
                new ErrorLoggingFilter()
        );
    }

    protected RequestSpecification publicApi() {
        return RestAssured.given()
                .baseUri("http://" + PLATFORM_URL)
                .accept("application/json")
                .contentType("application/json")
                .header("host", PLATFORM_URL);
    }
}