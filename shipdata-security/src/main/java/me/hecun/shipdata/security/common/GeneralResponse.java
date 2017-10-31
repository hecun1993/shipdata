package me.hecun.shipdata.security.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by hecun on 2017/10/25.
 */
@Data
@AllArgsConstructor
public class GeneralResponse<T> {

    private Integer code;
    private String message;
    private T data;

    public GeneralResponse(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }
}
