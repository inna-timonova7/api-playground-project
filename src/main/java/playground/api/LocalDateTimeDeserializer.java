package playground.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final Logger log = LoggerFactory.getLogger(LocalDateTimeDeserializer.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valueStr = p.getText();

        try {
            return deserialize(valueStr);
        } catch (DateTimeParseException e) {
            String errMessage = String.format("Can't parse string value to local date time. Value: %s", valueStr);
            log.info(errMessage, valueStr);
            throw new InvalidFormatException(p, errMessage, valueStr, LocalDateTime.class);
        }
    }

    public static LocalDateTime deserialize(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }
}
