package com.travel.service.user;

import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.common.SessionEntity;
import com.travel.model.user.UserRegisterDto;
import com.travel.model.user.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    String register(UserRegisterDto userRegisterDto) throws Exception;
    String login(UserRegisterDto userRegisterDto) throws Exception;
    PageResultEntity<UserVo> getAllUser(PageParams pageParams) throws Exception;
    SessionEntity getUserInfo(HttpServletRequest request);
    void logout(HttpServletRequest request);
}
