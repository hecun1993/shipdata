package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hecun on 2017/10/25.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
