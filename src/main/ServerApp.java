package main;

import dragon.*;
import reader.Reader;

import java.io.*;
import java.util.*;

public class ServerApp {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        DragonCollection dragonCollection = new DragonCollection();
        Server server = new Server(5000);

        Reader reader = new Reader();
        while (true) {
            try {
                try {
                    reader.readFile(dragonCollection);
                    break;
                } catch (NullPointerException e) {
                    System.out.println("Enter the name of file");
                    String fileName = scanner.nextLine();
                    reader.setFileWay(fileName);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Enter the name of file");
                String fileName = scanner.nextLine();
                reader.setFileWay(fileName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Unknown command");
            }
        }

        server.run(dragonCollection);
        dragonCollection.save();
    }
}
