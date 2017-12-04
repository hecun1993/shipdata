package me.hecun.shipdata.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 封装图形验证码
 *
 *
 * @author hecun
 * @date 2017/10/26
 */
@Data
@AllArgsConstructor
public class ImageCode {

    //随机数(图片上显示的数字)这个code要放到session中
    private String code;

    //验证码过期时间(过期的时间点!)
    private LocalDateTime expireTime;

    //展示给用户看的图片
    private BufferedImage image;

    //这个构造函数传入的是: 还有xx秒过期
    public ImageCode(BufferedImage image, String code, int expireTime) {
        this.code = code;
        this.image = image;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    //判断验证码是否过期
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
