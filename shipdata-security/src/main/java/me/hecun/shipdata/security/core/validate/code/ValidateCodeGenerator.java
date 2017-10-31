package me.hecun.shipdata.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成图形/短信验证码的接口
 * Created by hecun on 2017/10/26.
 */
public interface ValidateCodeGenerator {
    ImageCode generate(ServletWebRequest request);
}
