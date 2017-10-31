package me.hecun.shipdata.security.common.exception;

import lombok.Data;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 * Created by hecun on 2017/10/26.
 */
@Data
public class DataFileException extends RuntimeException {

    private Integer code;

    public DataFileException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DataFileException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
