package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 *
 * @author hecun
 * @date 2017/10/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserException extends RuntimeException {

    private Integer code;

    public UserException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
