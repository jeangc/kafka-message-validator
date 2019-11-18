public class Main {
    public static void main(String[] args) throws Exception {
        String servers = "localhost:9093,localhost:9094";

        Writer w = new Writer(servers);
        w.write("teste", "alo alo agora sim");

        Reader r = new Reader(servers, "my-group", "teste");
        r.run();
    }
}
