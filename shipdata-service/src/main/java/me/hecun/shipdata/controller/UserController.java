package me.hecun.shipdata.controller;

import me.hecun.shipdata.model.User;
import me.hecun.shipdata.security.SecurityUser;
import me.hecun.shipdata.security.ShipDataUserDetailService;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.UserNotLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hecun on 2017/10/25.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllUsers() {
        User user = new User();
        user.setUsername("hds");
        user.setPassword("123");

        GeneralResponse<User> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, user);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<GeneralResponse> getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        if (securityUser == null) {
            throw new UserNotLoginException(ResponseEnum.USER_NOT_LOGIN);
        } else {
            GeneralResponse<UserDetails> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, securityUser);
            return new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }

    }
}
