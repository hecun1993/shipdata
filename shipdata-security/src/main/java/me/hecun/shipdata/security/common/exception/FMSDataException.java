package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FMSDataException extends RuntimeException {

    private Integer code;

    public FMSDataException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
