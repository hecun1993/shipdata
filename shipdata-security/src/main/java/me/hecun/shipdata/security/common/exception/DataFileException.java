package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 *
 * @author hecun
 * @date 2017/10/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DataFileException extends RuntimeException {

    private Integer code;

    public DataFileException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
