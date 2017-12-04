package me.hecun.shipdata.security.common;

/**
 *
 * @author hecun
 * @date 2017/10/25
 */
public enum ResponseEnum {

    SUCCESS(0, "success!"),
    PARAMETER_ERROR(11, "parameter error!"),
    ERROR(10, "error!"),
    DATA_FILE_ERROR(12, "data file error!"),
    BATCH_JOB_ERROR(13, "batch job error!"),
    UNAUTHORIZED(20, "unauthorized"),
    USER_NOT_EXIST(21, "user not exist!"),
    USER_NOT_LOGIN(22, "Please Sign In"),
    USER_CREATE_ERROR(23, "create user error!"),
    SHIP_NOT_EXIST(31, "Ship not exist!"),
    MONITORDATA_NOT_EXIST(41, "Monitor data not exist! Please confirm the search params!"),
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
