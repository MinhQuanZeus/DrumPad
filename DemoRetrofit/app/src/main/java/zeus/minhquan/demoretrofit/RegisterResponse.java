package zeus.minhquan.demoretrofit;

/**
 * Created by QuanT on 5/23/2017.
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
