package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PositiveNumberValidator implements Validator {
    private String property;
    private ObjectMapper mapper = new ObjectMapper();

    public PositiveNumberValidator(String property) {
        this.property = property;
    }

    public void validate(String message) throws InvalidException {
        try {
            JsonNode rootNode = mapper.readTree(message);
            int value = Integer.parseInt(rootNode.findValue(property).toString());

            if (value < 0) {
                throw new InvalidException("Invalid positive number: " + value);
            }

        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }
}
