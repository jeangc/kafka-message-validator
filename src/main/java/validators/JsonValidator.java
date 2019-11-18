package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonValidator implements Validator {
    private ObjectMapper mapper = new ObjectMapper();

    public void validate(String message) throws InvalidException {
        try {
            mapper.readValue(message, JsonNode.class);
        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }
}
