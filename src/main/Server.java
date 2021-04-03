package main;

import data.*;
import dragon.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.NoSuchElementException;

public class Server {
    private int port;
    private DatagramSocket socket;
    private InetAddress address;
    //private static Scanner scanner = new Scanner(System.in);


    public Server(int port) {
        this.port = port;
    }

    public void run(DragonCollection dragonCollection) {

        try {
            ServerInput input = new ServerInput(dragonCollection);
            input.start();
            socket = new DatagramSocket(5000);
            DataForServer command = null;
            DataForClient message = null;
            Dragon dragon;

            while (true) {
                command =  getCommand();
                switch (command.getCommandName()) {
                    case "help":
                        message = new DataForClient(dragonCollection.help());
                        sendMessage(message);
                        break;
                    case "info":
                        message = new DataForClient(dragonCollection.info());
                        sendMessage(message);
                        break;
                    case "show":
                        message = new DataForClient(dragonCollection.show());
                        sendMessage(message);
                        break;
                    case "add":
                        dragon = (Dragon) command.getArgument();
                        message = new DataForClient(dragonCollection.add(dragon));
                        sendMessage(message);
                        break;
                    case "update":
                        if (dragonCollection.collectionSize() > 0) {
                            int updateId = (int) command.getArgument();
                            if (dragonCollection.checkIdForExistence(updateId)) {
                                dragonCollection.removeById(updateId);
                                message = new DataForClient("");
                                sendMessage(message);
                                command = getCommand();
                                dragon = (Dragon) command.getArgument();
                                message = new DataForClient(dragonCollection.update(dragon));
                                sendMessage(message);
                                break;
                            } else {
                                message = new DataForClient("There is no dragon with this id in the collection");
                                sendMessage(message);
                                break;
                            }
                        } else {
                            System.out.println("Collection is empty");
                        }
                        break;
                    case "remove_by_id":
                        if (dragonCollection.collectionSize() > 0) {
                            int id = (int) command.getArgument();
                            message = new DataForClient(dragonCollection.removeById(id));
                            sendMessage(message);
                        } else {
                            message = new DataForClient("Collection is already empty");
                            sendMessage(message);
                        }
                        break;
                    case "clear":
                        message = new DataForClient(dragonCollection.clear());
                        sendMessage(message);
                        break;
                    case "execute_script":
                        //param = dragonCollection.executeScript(parametrs[1]);
                        break;
                    case "head":
                        message = new DataForClient(dragonCollection.head());
                        sendMessage(message);
                        break;
                    case "remove_head":
                        message = new DataForClient(dragonCollection.removeHead());
                        sendMessage(message);
                        break;
                    case "add_if_max":
                        dragon = (Dragon) command.getArgument();
                        message = new DataForClient(dragonCollection.addIfMax(dragon));
                        sendMessage(message);
                        break;
                    case "sum_of_age":
                        message = new DataForClient(dragonCollection.sumOfAge());
                        sendMessage(message);
                        break;
                    case "filter_contains_name":
                        String name = (String) command.getArgument();
                        message = new DataForClient(dragonCollection.filterContainsName(name));
                        sendMessage(message);
                        break;
                    case "filter_less_than_age":
                        long age = (long) command.getArgument();
                        message = new DataForClient(dragonCollection.filterLessThanAge(age));
                        sendMessage(message);
                        break;
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DataForServer getCommand() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }

    private void sendMessage(DataForClient message) throws IOException {
        byte[] sendBuffer = serialize(message);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }

    private DataForServer deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        DataForServer command = (DataForServer) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return command;
    }

    private byte[] serialize(DataForClient message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
}
