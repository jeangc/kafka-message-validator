import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kafka.Writer;
import validators.InvalidException;
import validators.Validator;

import java.util.Collection;

public class Processor implements kafka.Processor {
    private Writer writer;
    private Collection<Validator> validators;
    private String validMessages;
    private String invalidMessages;

    private ObjectMapper mapper = new ObjectMapper();

    Processor(Writer writer, Collection<Validator> validators, String validMessages, String invalidMessages) {
        this.writer = writer;
        this.validators = validators;
        this.validMessages = validMessages;
        this.invalidMessages = invalidMessages;
    }

    public void process(String message) {
        try {
            for (Validator validator : validators) {
                validator.validate(message);
            }

            writer.write(validMessages, message);
        } catch (InvalidException e) {
            try {
                ObjectNode rootNode = mapper.createObjectNode();
                rootNode.put("error", e.getMessage());
                rootNode.put("message", message);
                writer.write(invalidMessages, mapper.writeValueAsString(rootNode));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }


        System.out.printf("Processing:(%s)\n", message);
    }
}
