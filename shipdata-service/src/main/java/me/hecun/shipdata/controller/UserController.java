package me.hecun.shipdata.controller;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.User;
import me.hecun.shipdata.security.SecurityUser;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.UserNotLoginException;
import me.hecun.shipdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hecun
 * @date 2017/10/25
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<GeneralResponse> getAllUsers() {
        List<User> users = userService.getAllUser();

        GeneralResponse<List<User>> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, users);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 用来检验用户是否登录
     *
     * @param securityUser
     * @return
     */
    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<GeneralResponse> getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        if (securityUser == null) {
            throw new UserNotLoginException(ResponseEnum.USER_NOT_LOGIN);
        } else {
            GeneralResponse<UserDetails> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, securityUser);
            return new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }
    }

    /**
     * 创建用户
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<GeneralResponse> createUser(User user) {

        userService.createUser(user);

        GeneralResponse<User> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, user);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 更新用户的接口
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<GeneralResponse> updateUser(@RequestParam("username") String username,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("email") String email) {
        User user = userService.updateUser(username, password, email);

        GeneralResponse<User> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, user);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 删除用户的接口
     */
    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<GeneralResponse> deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);

        GeneralResponse<User> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 登出的接口
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        try {
            response.sendRedirect("http://z.cn:4200");
        } catch (IOException e) {
            log.error("log out error");
            e.printStackTrace();
        }

    }
}
