package com.zhn.user.controller;

import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.bean.ResponseVO;
import com.zhn.user.dto.UserInsertDTO;
import com.zhn.user.dto.UserUpdateDTO;
import com.zhn.user.service.UserService;
import com.zhn.user.dto.UserLoginDTO;
import com.zhn.user.vo.UserLoginVO;
import com.zhn.user.vo.UserOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseVO<UserLoginVO> login(@Validated @RequestBody UserLoginDTO userDto, HttpServletResponse response) throws Exception {
        UserLoginVO userVO = userService.login(userDto);
        return new ResponseVO<>(userVO);
    }

    @PostMapping("/logout")
    public ResponseVO logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseVO<>();
    }

    @GetMapping("/pageUser")
    public ResponseVO<PageResultVO<UserLoginVO>> getPageUser(PageParamsDTO pageParamsDTO, String name) {
        return new ResponseVO<>(userService.getPageUser(pageParamsDTO, name));
    }

    @PostMapping("/user")
    public ResponseVO addUser(@Validated @RequestBody UserInsertDTO insertDTO) throws Exception {
        userService.insertUser(insertDTO);
        return new ResponseVO<>();
    }

    @PutMapping("/user")
    public ResponseVO updateUser(@Validated @RequestBody UserUpdateDTO updateDTO) throws Exception {
        userService.updateUser(updateDTO);
        return new ResponseVO<>();
    }

    @DeleteMapping("/user")
    public ResponseVO deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return new ResponseVO<>();
    }

    @GetMapping("/userOption")
    public ResponseVO<List<UserOptionVO>> getUserOption() {
        return new ResponseVO<>(userService.getUserOption());
    }
}
