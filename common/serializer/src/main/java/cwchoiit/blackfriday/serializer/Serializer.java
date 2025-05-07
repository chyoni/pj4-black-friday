package cwchoiit.blackfriday.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Serializer {

    private static final ObjectMapper objectMapper = initialize();

    private static ObjectMapper initialize() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("[deserialize] Failed to deserialize json. json: {}, clazz: {}", json, clazz, e);
            return null;
        }
    }

    public static <T> T deserialize(Object json, Class<T> clazz) {
        try {
            return objectMapper.convertValue(json, clazz);
        } catch (Exception e) {
            log.error("[deserialize] Failed to deserialize json. json: {}, clazz: {}", json, clazz, e);
            return null;
        }
    }

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[serialize] Failed to serialize object. object: {}", object, e);
            return null;
        }
    }
}
