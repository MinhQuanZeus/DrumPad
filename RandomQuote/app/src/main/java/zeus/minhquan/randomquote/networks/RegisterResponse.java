package zeus.minhquan.randomquote.networks;

/**
 * Created by QuanT on 5/27/2017.
 */

public class RegisterResponse {
    private String message;
    private int code;

    public RegisterResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
