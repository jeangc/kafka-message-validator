package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonValidator implements Validator {
    private ObjectMapper mapper = new ObjectMapper();

    public void validate(String message) throws InvalidException {
        try {
            JsonNode rootNode = mapper.readTree(message);

            String error = "";
            error = error.concat(validateField(rootNode, "event"));

            if (error.length() > 0) {
                throw new InvalidException(error);
            }

        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }

    private String validateField(JsonNode rootNode, String path) {
        if (!rootNode.has(path) || rootNode.path(path).isMissingNode()) {
            return path.concat(" is missing.");
        }

        return "";
    }
}
