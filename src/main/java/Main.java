import kafka.Reader;
import kafka.Writer;
import validators.JsonValidator;
import validators.Validator;

import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws Exception {
        String servers = "localhost:9093,localhost:9094";

        Reader reader = new Reader(servers, "my-group", "teste");
        Writer writer = new Writer(servers);

        Collection<Validator> validators = Arrays.asList(new JsonValidator());
        Processor p = new Processor(writer, validators, "valid", "invalid");
        reader.run(p);
    }
}
