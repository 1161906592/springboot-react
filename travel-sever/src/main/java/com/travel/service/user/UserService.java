package com.travel.service.user;

import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.model.user.UserRegisterDto;
import com.travel.model.user.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {
    String register(UserRegisterDto userRegisterDto, HttpSession session) throws Exception;
    String login(UserRegisterDto userRegisterDto, HttpSession session) throws Exception;
    PageResultEntity<UserVo> getAllUser(PageParams pageParams) throws Exception;
    void logout(HttpServletRequest request);
}
