public class Main {
    public static void main(String[] args) throws Exception {
        String servers = "localhost:9093,localhost:9094";

        Reader r = new Reader(servers, "my-group", "teste");
        Writer w = new Writer(servers);
        DefaultProcessor p = new DefaultProcessor(w, "valid", "invalid");
        r.run(p);
    }
}
