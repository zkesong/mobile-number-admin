package cn.qydx.hmdj.utils;

public class Response<T> {

    private T data;
    private int code;
    private String msg;
    private int count;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Response(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public Response(T data, int code, int count) {
        this.data = data;
        this.code = code;
        this.count = count;
    }

}
