import kafka.Reader;
import kafka.Writer;
import validators.CustomValidator;
import validators.JsonValidator;
import validators.Validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        String servers = "localhost:9093,localhost:9094";

        Writer writer = new Writer(servers);


        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            Reader reader = new Reader(servers, "my-first-group", "currency-messages");
            Collection<Validator> validators = Collections.singletonList(
                    new JsonValidator(Arrays.asList("event", "customer", "currency", "timestamp"))
            );
            Processor p = new Processor(writer, validators, "valid", "invalid");
            reader.run(p);
        });

        executor.submit(() -> {
            Reader reader = new Reader(servers, "my-second-group", "custom-messages");
            Collection<Validator> validators = Collections.singletonList(new CustomValidator());
            Processor p = new Processor(writer, validators, "valid", "invalid");
            reader.run(p);
        });
    }
}
