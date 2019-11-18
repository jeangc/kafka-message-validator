import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

class Reader {
    private String servers;
    private String groupId;
    private String topic;

    Reader(String servers, String groupId, String topic) {
        this.servers = servers;
        this.groupId = groupId;
        this.topic = topic;
    }

    void run(Processor processor) {
        Properties props = createConfig();
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(this.topic));

        while (true) {
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            records.forEach(record -> {
                processor.process(record.value());
            });
        }
    }

    private Properties createConfig() {
        final Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        return props;
    }

}