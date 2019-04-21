package com.travel.controller.user;

import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.common.ResponseEntity;
import com.travel.common.SessionEntity;
import com.travel.model.user.UserRegisterDto;
import com.travel.model.user.UserVo;
import com.travel.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterDto userRegisterDto) throws Exception {
        String token = userService.register(userRegisterDto);
        return new ResponseEntity<>(token);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserRegisterDto userRegisterDto) throws Exception {
        String token = userService.login(userRegisterDto);
        return new ResponseEntity<>(token);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<PageResultEntity<UserVo>> getAllUser(@Validated PageParams pageParams) throws Exception {
        return new ResponseEntity<>(userService.getAllUser(pageParams));
    }

    @GetMapping("/basicInfo")
    public ResponseEntity<SessionEntity> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getUserInfo(request));
    }
}
