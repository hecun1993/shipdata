package me.hecun.shipdata.security.browser;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hecun on 2017/10/25.
 */
@RestController
@Slf4j
public class BrowserSecurityController {

    //从session缓存中拿到引发跳转的请求, 目的是判断其来自html还是json
    private RequestCache requestCache = new HttpSessionRequestCache();

    //做http的请求跳转
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/authentication/require")
    public ResponseEntity<GeneralResponse> requireAuthentication(HttpServletRequest httpServletRequest,
                                                                 HttpServletResponse httpServletResponse) throws IOException {
        //拿到缓存在session中的request类信息
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);

        if (savedRequest != null) {
            //拿到引发跳转的请求
            String targetUrl = savedRequest.getRedirectUrl();

            log.info("引发跳转的请求是: " + targetUrl);
            log.info("跳转到的登录页是: " + securityProperties.getBrowser().getLoginPage());

            //判断引发跳转的请求是不是以html结尾(比如用户直接访问了index.html)
            //如果是, 则直接跳转到我们配置的登录页面中
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, securityProperties.getBrowser().getLoginPage());
            }
        }

        GeneralResponse<String> generalResponse = new GeneralResponse<>(ResponseEnum.UNAUTHORIZED, "您访问的服务需要身份认证, 请引导用户到登录页.");
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
