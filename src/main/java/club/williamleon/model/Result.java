package club.williamleon.model;

/**
 * Created by 53068 on 2018/4/16 0016.
 */

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * RESTful result class
 * @param <D>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<D> {

    private int code;
    private String message;
    private D data;

    public Result() {

    }

    public Result(int code, String message, D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
