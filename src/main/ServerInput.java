package main;

import dragon.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ServerInput extends Thread{
    private DragonCollection dragonCollection;
    private File file;

    public ServerInput(DragonCollection collection) {
        this.dragonCollection = collection;
        this.file = file;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command.equals("save")) {
                dragonCollection.save();
                System.out.println("Collection is saved to a file");
            } else {
                System.out.println("Command \"" + command + "\" doesn't exists");
            }
        }
    }
}
