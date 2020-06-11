package ObjectProtocol;

import java.io.Serializable;

public class ErrorResponse implements Response, Serializable {
    private String error;
    public ErrorResponse(String e) {
        error = e;
    }
    public String getError(){
        return error;
    }
}
