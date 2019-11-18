public class DefaultProcessor implements Processor {
    private Writer writer;
    private String validMessages;
    private String invalidMessages;

    DefaultProcessor(Writer writer, String validMessages, String invalidMessages) {
        this.writer = writer;
        this.validMessages = validMessages;
        this.invalidMessages = invalidMessages;
    }

    public void process(String message) {
        writer.write(validMessages, message);

        System.out.printf("Processing:(%s)\n", message);
    }
}
