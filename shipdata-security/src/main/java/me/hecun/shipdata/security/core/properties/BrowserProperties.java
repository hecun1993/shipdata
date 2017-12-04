package me.hecun.shipdata.security.core.properties;

import lombok.Data;

/**
 *
 * @author hecun
 * @date 2017/10/25
 */
@Data
public class BrowserProperties {

    //默认登录页面
    private String loginPage = "/default-login.html";

    //默认登录方式的配置是json
    private LoginType loginType = LoginType.JSON;

    //默认错误页面
    private String errorPage = "/default-error.html";

    //记住我的过期时长
    private int rememberMeSeconds = 3600;
}
