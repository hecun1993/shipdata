package me.hecun.shipdata.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author hecun
 * @date 2017/10/25
 */
@ConfigurationProperties(prefix = "shipdata.security")
@Data
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    //图形验证码的默认配置
    private ValidateCodeProperties code = new ValidateCodeProperties();
}
