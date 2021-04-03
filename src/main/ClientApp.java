package main;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client("localhost", 5000);
        client.run();
    }
}
