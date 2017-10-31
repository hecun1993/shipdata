package me.hecun.shipdata.security.core.validate.code;

import me.hecun.shipdata.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 重构图形验证码接口:
 *  验证码基本参数可以配置
 *      请求中/code/image?width=200
 *      应用中配置: width=100 length=6
 *      默认配置: length=4
 *      从上往下逐渐覆盖, 最终的结果是: width=200, length=6
 *
 *  验证码拦截的接口可以配置
 *      在ImageCodeProperties中加一个url的属性, 在yml文件中配置url为/user,/user/*
 *      然后在ValidateCodeFilter中实现InitializingBean, 构造出所有的需要进行图形验证码校验的url的集合
 *      遍历这个集合, 如果request.getRequestURI()与urls集合中的请求互相匹配, 则需要校验
 *      也就是说, 当登录成功后, 访问/user请求, 会提示, 验证码的值不能为空
 *
 *  验证码的生成逻辑可以配置
 *      抽象到一个接口中: ValidateCodeGenerator: ImageCode generate(ServletWebRequest request);
 *      实现ValidateCodeGenerator接口的类: ImageCodeGenerator
 *      然后把原先在ValidateCodeController中生成验证码的代码放在ImageCodeGenerator中, 在ValidateCodeController中注入ImageCodeGenerator即可
 *
 *      下面需要把验证码的生成逻辑, 也就是generate方法做成可以配置的
 *      创建一个ValidateCodeBeanConfig
 *      来配置ImageCodeGenerator, 把它放入spring容器中, 名字就是方法名imageCodeGenerator
 *      @OnConditioinalMissingBean(name="imageCodeGenerator")
 *      如果spring容器中有应用级的DemoImageCodeGenerator, 其在spring中的名字也是ImageCodeGenerator, 则会使用应用级的图形验证码生成逻辑
 *
 *
 * !验证图形验证码是否正确的类!
 *  在用户名密码过滤器之前,加一个验证码验证的过滤器
 *  要继承Spring提供的工具类OncePerRequestFilter, 保证这个过滤器每次只会被调用一次
 *
 * Created by hecun on 2017/9/30.
 */
@Component
//实现InitializingBean接口的目的是为了初始化所有需要验证码校验的urls属性的值
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private SecurityProperties securityProperties;

    //注入失败处理器
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        if (securityProperties.getCode().getImage().getUrl() != null) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
            urls.addAll(Arrays.asList(configUrls));
        }

        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, httpServletRequest.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    //校验验证码的逻辑
    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {

        //拿到session中的验证码的code值
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

        //拿到输入请求的表单中提交的验证码imagecode的值, 因为name="imageCode"
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        //比对是否匹配
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
