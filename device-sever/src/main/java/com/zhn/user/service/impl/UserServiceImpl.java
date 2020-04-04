package com.zhn.user.service.impl;

import com.github.pagehelper.Page;
import com.zhn.common.bean.PageParamsDTO;
import com.zhn.common.bean.PageResultVO;
import com.zhn.common.bean.SessionEntity;
import com.zhn.common.dao.Dao;
import com.zhn.common.exception.BusinessException;
import com.zhn.common.utils.StringUtils;
import com.zhn.user.bean.User;
import com.zhn.user.dto.UserInsertDTO;
import com.zhn.user.dto.UserLoginDTO;
import com.zhn.user.dto.UserQueryDTO;
import com.zhn.user.dto.UserUpdateDTO;
import com.zhn.user.service.UserService;
import com.zhn.user.vo.UserLoginVO;
import com.zhn.common.utils.CacheUtils;
import com.zhn.user.vo.UserOptionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Dao dao;

    @Override
    public UserLoginVO login(UserLoginDTO userDto) throws Exception {
        UserQueryDTO queryDTO = new UserQueryDTO();
        queryDTO.setTel(userDto.getTel());
        User user = dao.getOne("com.zhn.user.UserMapper.findUser", queryDTO);
        if (user == null) {
            throw new BusinessException("用户名不存在!");
        }
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new BusinessException("密码错误!");
        }
        SessionEntity sessionEntity = new SessionEntity();
        // 存session
        BeanUtils.copyProperties(user, sessionEntity);
        String token = CacheUtils.setCache(sessionEntity);
        // 返回前端
        UserLoginVO userVO = new UserLoginVO();
        BeanUtils.copyProperties(sessionEntity, userVO);
        userVO.setToken(token);
        return userVO;
    }

    @Override
    public void logout(HttpServletRequest request) {
        CacheUtils.removeCache(request.getHeader("token"));
    }

    @Override
    public PageResultVO<UserLoginVO> getPageUser(PageParamsDTO pageParamsDTO, String name) {
        UserQueryDTO queryDTO = new UserQueryDTO();
        queryDTO.setName(name);
        Page<UserLoginVO> pageResultVO = dao.getPageList("com.zhn.user.UserMapper.findUser", queryDTO, pageParamsDTO);
        return new PageResultVO<>(pageResultVO);
    }

    @Override
    public void insertUser(UserInsertDTO insertDTO) throws Exception {
        insertDTO.setId(StringUtils.getUUid());
        int count = dao.insert("com.zhn.user.UserMapper.insertUser", insertDTO);
        if (count == 0) {
            throw new BusinessException("手机号已存在");
        }
    }

    @Override
    public void updateUser(UserUpdateDTO updateDTO) throws Exception {
        int count = dao.update("com.zhn.user.UserMapper.updateUser", updateDTO);
        if (count == 0) {
            throw new BusinessException("手机号已存在");
        }
    }

    @Override
    public void deleteUser(String id) {
        dao.delete("com.zhn.user.UserMapper.deleteUser", id);
    }

    @Override
    public List<UserOptionVO> getUserOption() {
        return dao.getList("com.zhn.user.UserMapper.findUser");
    }
}
