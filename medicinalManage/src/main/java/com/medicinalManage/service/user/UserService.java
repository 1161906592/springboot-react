package com.medicinalManage.service.user;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.model.user.UserRegisterDto;
import com.medicinalManage.model.user.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {
    String register(UserRegisterDto userRegisterDto, HttpSession session) throws Exception;
    String login(UserRegisterDto userRegisterDto, HttpSession session) throws Exception;
    PageResultEntity<UserVo> getAllUser(PageParams pageParams) throws Exception;
    void logout(HttpServletRequest request);
}
