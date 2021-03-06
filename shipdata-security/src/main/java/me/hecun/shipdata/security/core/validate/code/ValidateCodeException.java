package me.hecun.shipdata.security.core.validate.code;


import org.springframework.security.core.AuthenticationException;

/**
 * 自己创建的验证码校验错误异常类
 *
 * 所有身份认证中的校验异常, 都要继承Spring提供的AuthenticationException
 *
 * @author hecun
 * @date 2017/10/26
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -8797794748903286545L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
