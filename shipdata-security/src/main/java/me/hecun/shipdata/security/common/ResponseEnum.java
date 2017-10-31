package me.hecun.shipdata.security.common;

/**
 * Created by hecun on 2017/10/25.
 */
public enum ResponseEnum {

    SUCCESS(0, "success!"),
    UNAUTHORIZED(20, "unauthorized"),
    USER_NOT_EXIST(21, "user not exist!"),
    USER_NOT_LOGIN(22, "user not login!"),
    ERROR(10, "error!"),
    DATA_FILE_ERROR(12, "data file error!"),
    BATCH_JOB_ERROR(13, "batch job error!")
    ;

    public int code;
    public String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
