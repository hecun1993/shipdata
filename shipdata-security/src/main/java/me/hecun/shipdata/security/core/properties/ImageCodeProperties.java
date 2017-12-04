package me.hecun.shipdata.security.core.properties;

import lombok.Data;

/**
 *
 * @author hecun
 * @date 2017/10/26
 */
@Data
public class ImageCodeProperties {

    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expireIn = 60;

    //用于指定哪些url需要被图形验证码过滤器拦截
    private String url;
}
