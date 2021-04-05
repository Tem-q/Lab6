package dragon;

import data.DataForServer;
import reader.Reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class that manages the dragon collection
 */
public class DragonCollection {
    private Deque<Dragon> dragons = new ArrayDeque<>();

    /**
     * Method returns size of collection
     * @return dragons.size
     */
    public int collectionSize() {
        return dragons.size();
    }


    /**
     * Method prints the legend
     * @return String message fo client
     */
    public String help() {
        return ("List of available commands: \n"
                + "help: display help for available commands \n"
                + "info: display information about the collection \n"
                + "show: display all the elements of the collection \n"
                + "add: add a new element to the collection \n"
                + "update id: update the value of a collection element by its id \n"
                + "remove_by_id id: delete an element from the collection by its id \n"
                + "clear: clear the collection \n"
                + "save: save the collection to a file \n"
                + "execute_script file name: execute the script from the specified file \n"
                + "exit: end the program (without saving it to a file) \n"
                + "head: print the first element of the collection \n"
                + "remove_head: print the first element of the collection and delete it \n"
                + "add_if_max: add a new element to the collection if its value exceeds the value of the largest element in this collection \n"
                + "sum_of_age: print the sum of the values of the age field for all the elements of the collection \n"
                + "filter_contains_name name: print elements whose name field value contains the specified substring \n"
                + "filter_less_than_age age: print elements whose age field value is less than the specified value");
    }

    /**
     * Method displays information about the collection
     * @return String message fo client
     */
    public String info() {
        return (dragons.getClass().toString() + ", size: " + dragons.size());
    }

    /**
     * Method displays all the elements of the collection
     * @return String message fo client
     */
    public String show() {
        if (!dragons.isEmpty()) {
            StringBuilder ss = new StringBuilder();
            dragons.forEach(v->ss.append(v.toString()).append("\n"));
            return ss.toString();
        } else {
            return ("Collection is empty");
        }

    }

    /**
     * Method adds the dragon to collection from file
     * @param dragon
     * @return String message fo client
     */
    public void addFromReader (Dragon dragon) {
        dragon.setId(dragon.findMaxId(dragons));
        dragons.offer(dragon);
    }

    /**
     * Method adds a new element to the collection
     * @param dragon
     * @return String message fo client
     */
    public String add(Dragon dragon) {
        dragon.setId(dragon.findMaxId(dragons));
        dragons.offer(dragon);
        return ("Dragon successfully added");
    }

    /**
     * Method updates the value of a collection element by its id
     * @param dragon
     * @return String message fo client
     */
    public String update(Dragon dragon) {
        dragons.offer(dragon);
        return ("Dragon successfully updated");
    }

    /**
     * Method removes an element from the collection by its id
     * @param id
     * @return String message fo client
     */
    public String removeById(int id) {
        if (dragons.stream().filter(d -> d.getId() == id).count() == 1) {
            dragons.removeIf(d -> d.getId() == id);
            return ("Dragon with id = " + id + " successfully deleted");
        } else {
            return ("There is no dragon with this id in the collection");
        }

    }

    /**
     * Method clears the collection
     * @return String message fo client
     */
    public String clear() {
        if (!dragons.isEmpty()) {
            dragons.clear();
            return ("Collection cleared successfully");
        } else {
            return ("Collection is already empty");
        }
    }

    /**
     * Method saves the collection to a file
     */
    public void save() {
        try {
            Reader reader = new Reader();
            reader.clearFile();
            for (Dragon d: dragons) {
                reader.writeFile(d);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No such file or directory");
        }
    }

    /**
     * Method executes the script from the specified file
     * @param scriptName
     * @return checkExit
     */
    public String executeScript(String scriptName) {
        String message = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(scriptName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String line;
        try {
            while (((line = bufferedReader.readLine()) != null)) {
                String[] parametrs = new String[]{"",""};
                parametrs = checkTask(line, parametrs);
                line = parametrs[0];
                switch (line) {
                    case "help":
                        message = help();
                        break;
                    case "info":
                        message = info();
                        break;
                    case "show":
                        message = show();
                        break;
                    case "add":
                        message = "";
                        break;
                    case "remove_by_id":
                        if (!parametrs[1].equals("")) {
                            try {
                                int id = Integer.parseInt(parametrs[1]);
                                message = removeById(id);
                            } catch (NumberFormatException e) {
                                message = ("The id must be int type. Change it in a file");
                            }
                        } else {
                            message = ("There is no id in the file");
                        }
                        break;
                    case "clear":
                        message = clear();
                        break;
                    case "head":
                        message = head();
                        break;
                    case "remove_head":
                        message = removeHead();
                        break;
                    case "sum_of_age":
                        message = sumOfAge();
                        break;
                    case "filter_contains_name":
                        if (!parametrs[1].equals("")) {
                            String name = parametrs[1];
                            message = filterContainsName(name);
                        } else {
                            message = ("There is no name in the file");
                        }
                        break;
                    case "filter_less_than_age":
                        if (!parametrs[1].equals("")) {
                            try {
                                long age = Long.parseLong(parametrs[1]);
                                message = filterLessThanAge(age);
                            } catch (NumberFormatException e) {
                                message = ("The age must be long type. Change it in a file");
                            }
                        } else {
                            message = ("There is no age in the file");
                        }
                        break;
                    default:
                        message = "Unknown command. Please change it in a file";
                }
            }
        } catch (IOException e) {
            message = "No such file or directory";
        } catch (NullPointerException e) {
            message = "No such file or directory";
        }
        return message;
    }

    /**
     * Method prints the first element of the collection
     * @return String message fo client
     */
    public String head() {
        if (dragons.size() > 0) {
            return ("The first element of collection:" + dragons.getFirst());
        } else {
            return ("Collections is empty");
        }
    }

    /**
     * Method prints the first element of the collection and removes it
     * @return String message fo client
     */
    public String removeHead() {
        if (dragons.size() > 0) {
            System.out.println(dragons.pollFirst());
            return ("The first element of collection successfully deleted");
        } else {
            return ("Collections is empty");
        }
    }

    /**
     * Method adds a new element to the collection if its value exceeds the value of the largest element in this collection
     * @param dragon
     * @return String message fo client
     */
    public String addIfMax(Dragon dragon) {
        if (dragons.stream().noneMatch(d -> d.getWeight() > dragon.getWeight())) {
            dragons.offer(dragon);
            return ("Dragon successfully added");
        } else {
            return ("The weight of the dragon is not the maximum in the collection");
        }
    }

    /**
     * Method prints the sum of the values of the age field for all the elements of the collection
     * @return String message fo client
     */
    public String sumOfAge() {
        long sum = 0;
        for (Dragon d: dragons) {
            sum += d.getAge();
        }
        return ("Sum of dragon's age is " + sum);
    }

    /**
     * Method prints elements whose name field value contains the specified substring
     * @param name
     * @return String message fo client
     */
    public String filterContainsName(String name) {
        StringBuilder ss = new StringBuilder();
        dragons.stream().filter(d -> d.getName().contains(name)).forEach(d->ss.append(d.toString()).append("\n"));
        if (dragons.stream().anyMatch(d -> d.getName().contains(name))) {
            return ss.toString();
        } else {
            return ("There are no elements containing this substring");
        }
    }

    /**
     * Method prints elements whose age field value is less than the specified value
     * @param age
     * @return String message fo client
     */
    public String filterLessThanAge(long age) {
        StringBuilder ss = new StringBuilder();
        dragons.stream().filter(d -> d.getAge() < age).forEach(d->ss.append(d.toString()).append("\n"));
        if (dragons.stream().filter(d -> d.getAge() < age).count() > 0) {
            return ss.toString();
        } else {
            return ("The values of the age field for all elements are greater than or equal to " + age);
        }
    }

    /**
     * Method finds a dragon with the specified id
     * @param updateId
     * @return checkId
     */
    public boolean checkIdForExistence(int updateId) {
        boolean checkId = false;
        for (Dragon d: dragons) {
            if (d.getId() == updateId) {
                checkId = true;
            }
        }
        return checkId;
    }

    /**
     * Method compares the specified age with the ages of the dragons
     * @param maxWeight
     * @return checkWeight
     */
    public boolean checkWeightIfMax(int maxWeight) {
        boolean checkWeight = false;
        int count = 0;
        for (Dragon d: dragons) {
            if (d.getWeight() < maxWeight) {
                count++;
            }
        } if (count == dragons.size()) {
            checkWeight = true;
        }
        return checkWeight;
    }


    /**
     * Method finds a command from the list in the input line
     * @param task
     * @return parametrs[]
     */
    public String[] checkTask(String task, String[] parametrs) {
        String[] command = task.split(" ");
        for (int i = 0; i<command.length; i++) {
            if ((command[i].equals("help")) || (command[i].equals("info")) || (command[i].equals("show")) || (command[i].equals("add")) ||
                    (command[i].equals("clear")) || (command[i].equals("exit")) || (command[i].equals("head")) ||
                    (command[i].equals("remove_head")) || (command[i].equals("add_if_max")) || (command[i].equals("sum_of_age"))) {
                parametrs[0] = command[i];
                break;
            }
            try {
                if ((command[i].equals("update")) || (command[i].equals("remove_by_id")) || (command[i].equals("execute_script")) ||
                        (command[i].equals("filter_contains_name")) || (command[i].equals("filter_less_than_age"))) {
                    parametrs[0] = command[i];
                    parametrs[1] = command[i+1];
                    break;
                } else {
                    parametrs[0] = "";
                }
            } catch (IndexOutOfBoundsException e) {
                parametrs[0] = command[i];
                parametrs[1] = "";

            }
        }
        return parametrs;
    }
}
