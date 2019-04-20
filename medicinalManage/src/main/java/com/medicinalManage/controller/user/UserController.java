package com.medicinalManage.controller.user;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.ResponseEntity;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.model.user.UserRegisterDto;
import com.medicinalManage.model.user.UserVo;
import com.medicinalManage.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    // 菜单权限测试
    @GetMapping("/sys/menu")
    public ResponseEntity<String[]> test() {
        String[] strings = {"/home/batch", "/home/batch/detail", "/home/medicinal", "/home/manufacturer"};
        return new ResponseEntity<>(strings);
    }
    @PostMapping("/user/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterDto userRegisterDto, HttpSession session) throws Exception {
        String token = userService.register(userRegisterDto, session);
        return new ResponseEntity<>(token);
    }
    @PostMapping("/user/login")
    public ResponseEntity login(@Validated @RequestBody UserRegisterDto userRegisterDto, HttpSession session) throws Exception {
        String token = userService.login(userRegisterDto, session);
        return new ResponseEntity<>(token);
    }
    @PostMapping("/user/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity();
    }
    @GetMapping("/user/all")
    public ResponseEntity<PageResultEntity<UserVo>> getAllUser (@Validated PageParams pageParams) throws Exception {
        return new ResponseEntity<>(userService.getAllUser(pageParams));
    }
}
