package me.hecun.shipdata.security;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.User;
import me.hecun.shipdata.repository.UserRepository;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author hecun
 * @date 2017/10/25
 */
@Component("userDetailsService")
@Slf4j
public class ShipDataUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户输入的用户名是: " + username);
        //1.根据用户登录时输入的用户名, 从数据库中查出这个用户的信息
        User loginUser = userRepository.findByUsername(username);
        log.info("{}", loginUser);

        if (loginUser != null) {
            //2.返回UserDetails的实现类
            return new SecurityUser(username, loginUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("OPERATOR"));
        } else {
            throw new UserNotExistException(ResponseEnum.USER_NOT_EXIST);
        }
    }
}
