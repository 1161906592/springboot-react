package com.zhn.user.service;

import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.user.dto.UserInsertDTO;
import com.zhn.user.dto.UserLoginDTO;
import com.zhn.user.dto.UserUpdateDTO;
import com.zhn.user.vo.UserLoginVO;
import com.zhn.user.vo.UserOptionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    UserLoginVO login(UserLoginDTO userRegisterDto) throws Exception;
    void logout(HttpServletRequest request);
    PageResultVO<UserLoginVO> getPageUser(PageParamsDTO pageParamsDTO, String name);
    void insertUser(UserInsertDTO userInsertDTO) throws Exception;
    void updateUser(UserUpdateDTO userUpdateDTO) throws Exception;
    void deleteUser(String id);
    List<UserOptionVO> getUserOption();
}
