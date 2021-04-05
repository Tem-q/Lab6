package main;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client("localhost", 5000);
        client.run();
    }
}
