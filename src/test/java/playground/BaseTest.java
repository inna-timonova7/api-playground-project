package playground;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

public class BaseTest {
    protected static final String platformUrl = System.getProperty("localhost.target.platform.url", "localhost:3030");

    public BaseTest() {
        RestAssured.config = RestAssuredConfig
                .config()
                .objectMapperConfig(
                        new ObjectMapperConfig().jackson2ObjectMapperFactory(
                                (cls, charset) -> {
                                    SimpleModule module = new SimpleModule();
//                                            .addDeserializer(UUID.class, new StringUUIDDeserializer())
//                                            .addSerializer(UUID.class, new UUIDStringSerializer())
//                                            .addDeserializer(ScheduleItemType.class, new
//                                            ScheduleItemTypeDeserializer())
//                                            .addDeserializer(ZonedTime.class, new ZonedTimeDeserializer())
//                                            .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
//                                            .addDeserializer(Duration.class, new JsonDeserializer<>() {
//                                                @Override
//                                                public Duration deserialize(JsonParser p, DeserializationContext
//                                                ctxt) {
//                                                    return Duration.ZERO;
//                                                }
//                                            });

                                    return new ObjectMapper()
                                            .findAndRegisterModules()
                                            .registerModule(module)
                                            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
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
//                .basePath("/api")
                .baseUri("http://" + platformUrl)
                .accept("application/json")
                .header("host", platformUrl);
    }
//    protected RequestSpecification publicApiXML() {
//        return publicApi().accept("application/xml");
//    }
}