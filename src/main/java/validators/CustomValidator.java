package validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomValidator implements Validator {
    private ObjectMapper mapper = new ObjectMapper();

    public void validate(String message) throws InvalidException {
        try {
            JsonNode rootNode = mapper.readTree(message);
            System.out.println("Aqui: " + rootNode.findValue("event"));


        } catch (IOException e) {
            throw new InvalidException("Invalid JSON");
        }
    }
}
