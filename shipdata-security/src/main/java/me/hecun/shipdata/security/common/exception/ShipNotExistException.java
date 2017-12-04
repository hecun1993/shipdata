package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 * @author cun.he
 * @date 2017-11-30 下午10:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ShipNotExistException extends RuntimeException {

    private Integer code;

    public ShipNotExistException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
