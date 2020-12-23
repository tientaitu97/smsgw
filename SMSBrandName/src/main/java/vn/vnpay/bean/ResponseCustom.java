package vn.vnpay.bean;

public enum ResponseCustom {

    SUCCESS(200, "SUCCESS"),
    LOGIN_FAIL(201, "INVALID USERNAME OR PASSWORD"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "ACCESS DENIED"),
    SERVER_INTERNAL(500, "SERVER INTERNAL");

    private int code;
    private String description;

    ResponseCustom(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
