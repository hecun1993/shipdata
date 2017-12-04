package me.hecun.shipdata.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author hecun
 * @date 2017/10/26
 */
@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    //这里注入的, 可能是默认的验证码生成逻辑(bean的名字是imageCodeGenerator), 也可能是应用级的验证码生成逻辑(bean的名字也是imageCodeGenerator)
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    //操作session的工具类
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //生成图形验证码
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) throws IOException {
        //1.生成ImageCode
        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(httpServletRequest));

        //2.把ImageCode的code放入session中
        //第一个参数是说从request中拿session, 所以需要把request传入
        sessionStrategy.setAttribute(new ServletWebRequest(httpServletRequest), SESSION_KEY, imageCode);

        //3.将图片写到接口的响应中
        ImageIO.write(imageCode.getImage(), "JPEG", httpServletResponse.getOutputStream());
    }
}
