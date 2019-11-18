package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ListValidator implements Validator {
    private String property;
    private List<String> availableValues;
    private ObjectMapper mapper = new ObjectMapper();

    public ListValidator(String property, List<String> availableValues) {
        this.property = property;
        this.availableValues = availableValues;
    }

    public void validate(String message) throws InvalidException {
        try {
            JsonNode rootNode = mapper.readTree(message);
            String type = rootNode.findValue(property).asText();

            if (!availableValues.contains(type)) {
                throw new InvalidException("Unknown type: " + type);
            }

        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }
}
