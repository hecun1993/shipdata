package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hecun on 2017/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test01() {
        User user = new User();
        user.setUsername("hds");
        String password = passwordEncoder.encode("123");
        user.setPassword(password);
        user.setAuthority("ROLE_ADMIN");

        userRepository.save(user);
    }
}