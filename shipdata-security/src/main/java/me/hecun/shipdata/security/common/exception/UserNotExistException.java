package me.hecun.shipdata.security.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 * Created by hecun on 2017/10/26.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotExistException extends RuntimeException {

    private Integer code;

    public UserNotExistException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public UserNotExistException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
