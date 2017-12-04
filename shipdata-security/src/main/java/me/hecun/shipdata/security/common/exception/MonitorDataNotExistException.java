package me.hecun.shipdata.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.hecun.shipdata.security.common.ResponseEnum;

/**
 * @author cun.he
 * @date 2017-12-03 上午2:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class MonitorDataNotExistException extends RuntimeException {

    private Integer code;

    public MonitorDataNotExistException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
