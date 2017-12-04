package me.hecun.shipdata.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author hecun
 * @date 2017/10/25
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 用户名
     */
    @NotEmpty
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "email must have correct format!")
    private String email;

    /**
     * 角色
     */
    private String role;
}
