package me.hecun.shipdata.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hecun on 2017/10/25.
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    //用户名
    private String username;
    //密码
    private String password;
    //权限(16为普通权限)
    private String authority;
}
