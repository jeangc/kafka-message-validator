public class Main {
    public static void main(String[] args) throws Exception {
        Reader r = new Reader("localhost:9093,localhost:9094", "my-group", "teste");
        r.run();
    }
}
