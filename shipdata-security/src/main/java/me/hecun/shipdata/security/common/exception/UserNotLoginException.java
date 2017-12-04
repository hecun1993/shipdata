package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 *
 * @author hecun
 * @date 2017/10/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserNotLoginException extends RuntimeException {

    private Integer code;

    public UserNotLoginException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
