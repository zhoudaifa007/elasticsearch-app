package org.spring.springboot.redis.model;

/**
 * Created by zhoudf2 on 2017-9-21.
 */
public class ServerResponse<T> {
    private int code = 200;
    private String msg = "success";
    private T result;

    public ServerResponse(T result) {
        this.result = result;
    }

    public ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(String errorCode, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.result = data;
    }

    public static ServerResponse successObject() {
        return new ServerResponse(new Object());
    }

    public static ServerResponse successArray() {
        return new ServerResponse(new Object[0]);
    }

    public static <T> ServerResponse<T> successWithData(T data) {
        return new ServerResponse(data);
    }

    public static ServerResponse failureWithCodeMsg(int code, String msg) {
        return new ServerResponse(code, msg);
    }

    public static <T> ServerResponse<T> failureWithCodeMsg(String errorCode, String msg, T data) {
        return new ServerResponse(errorCode, msg, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
