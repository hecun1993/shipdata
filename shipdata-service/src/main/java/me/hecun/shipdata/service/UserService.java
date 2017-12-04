package me.hecun.shipdata.service;

import me.hecun.shipdata.model.User;

import java.util.List;

/**
 *
 * @author hecun
 * @date 2017/10/31
 */
public interface UserService {

    /**
     * 根据用户名查询单个用户
     */
    User getUser(String username);

    /**
     * 查询所有用户
     */
    List<User> getAllUser();

    /**
     * 创建新用户
     */
    void createUser(User user);

    /**
     * 更新用户, 根据用户名查到用户, 然后更新其email
     */
    User updateUser(String username, String password, String email);

    /**
     * 删除用户
     */
    void deleteUser(String username);
}
