package com.exam.model;


public class ApiResult<T> {
    /**
     * 回傳碼
     * 成功回傳200
     */
    private int code;

    /**
     * 錯誤訊息
     */
    private String message;

    /**
     * 回傳結果包在T裡
     */
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
