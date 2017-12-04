package me.hecun.shipdata.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.security.core.properties.LoginType;
import me.hecun.shipdata.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功后的处理逻辑
 *
 *
 * @author hecun
 * @date 2017/10/25
 */
@Component("authenticationSuccessHandler")
@Slf4j
public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    //spring启动时, 会自动生成这个对象, 用来把Authentication对象转成json
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    //登录成功时, 调用此方法
    //Authentication: 封装认证信息(发起认证请求的ip,session,认证通过之后返回的用户信息UserDetails)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功!");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            //把authentication对象以json形式返回给前端
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            response.sendRedirect("http://z.cn:4200");
        }
    }
}
