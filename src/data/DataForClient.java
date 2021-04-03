package data;

import java.io.Serializable;

public class DataForClient implements Serializable {
    private String message;

    public DataForClient(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
