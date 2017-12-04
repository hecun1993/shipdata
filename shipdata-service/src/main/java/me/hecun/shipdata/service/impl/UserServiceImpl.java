package me.hecun.shipdata.service.impl;

import me.hecun.shipdata.model.User;
import me.hecun.shipdata.repository.UserRepository;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.UserException;
import me.hecun.shipdata.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service
 *
 * @author hecun
 * @date 2017/10/31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new UserException(ResponseEnum.PARAMETER_ERROR);
        }
        //判断能否从数据库中查到这个用户
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException(ResponseEnum.USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        if (user == null) {
            throw new UserException(ResponseEnum.USER_CREATE_ERROR);
        }

        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new UserException(ResponseEnum.PARAMETER_ERROR);
        } else {
            String newPassword = passwordEncoder.encode(password);
            user.setPassword(newPassword);
        }
        userRepository.save(user);
    }

    @Override
    public User updateUser(String username, String password, String email) {
        //判断是否传入了用户名, 如果没有传入, 则无法更新, 参数错误
        if (StringUtils.isEmpty(username)) {
            throw new UserException(ResponseEnum.PARAMETER_ERROR);
        }
        //判断能否从数据库中查到这个用户
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException(ResponseEnum.USER_NOT_EXIST);
        }

        //如果传入的password不是空, 才更新, 否则不更新
        if (!StringUtils.isEmpty(email)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        //如果传入的email不是空, 才更新, 否则不更新
        if (!StringUtils.isEmpty(email)) {
            user.setEmail(email);
        }

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new UserException(ResponseEnum.PARAMETER_ERROR);
        }
        //判断能否从数据库中查到这个用户
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException(ResponseEnum.USER_NOT_EXIST);
        }

        userRepository.delete(user);
    }
}
