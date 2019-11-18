package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonValidator implements Validator {
    private List<String> fields;
    private ObjectMapper mapper = new ObjectMapper();

    public JsonValidator(List<String> fields) {
        this.fields = fields;
    }

    public void validate(String message) throws InvalidException {
        try {
            JsonNode rootNode = mapper.readTree(message);

            if (rootNode == null) {
                throw new InvalidException("Empty message");
            }

            ArrayList<String> errors = new ArrayList<>();

            for (String field : fields) {
                String error = validateField(rootNode, field);
                if (error != null) {
                    errors.add(error);
                }
            }

            if (errors.size() > 0) {
                throw new InvalidException(String.join(", ", errors));
            }

        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }

    private String validateField(JsonNode rootNode, String path) {
        if (!rootNode.has(path) || rootNode.path(path).isMissingNode()) {
            return path.concat(" is missing");
        }

        return null;
    }
}
