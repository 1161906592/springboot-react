package com.travel.serviceImpl.user;

import com.github.pagehelper.Page;
import com.travel.common.PageParams;
import com.travel.common.PageResultEntity;
import com.travel.common.SessionEntity;
import com.travel.dao.Dao;
import com.travel.exception.BusinessException;
import com.travel.model.user.UserDo;
import com.travel.model.user.UserRegisterDto;
import com.travel.model.user.UserVo;
import com.travel.service.user.UserService;
import com.travel.utils.CacheUtils;
import com.travel.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Dao dao;

    @Transactional(rollbackFor = { RuntimeException.class, BusinessException.class })
    @Override
    public String login(UserRegisterDto userRegisterDto, HttpSession session) throws Exception {
        UserDo userDo = new UserDo();
        userDo.setUserName(userRegisterDto.getUserName());
        UserDo user = dao.getOne("com.travel.mapper.user.UserDoMapper.getUserByUserDo", userDo);
        if (user == null) {
            throw new BusinessException("用户名不存在!");
        }
        if (!user.getPassword().equals(userRegisterDto.getPassword())) {
            throw new BusinessException("密码错误!");
        }
        SessionEntity sessionEntity = new SessionEntity();
        BeanUtils.copyProperties(user, sessionEntity);
        return CacheUtils.setCache(sessionEntity);
    }

    @Override
    public void logout(HttpServletRequest request) {
        CacheUtils.removeCache(request.getHeader("token"));
    }

    @Override
    public PageResultEntity<UserVo> getAllUser(PageParams pageParams) throws Exception {
        Page<UserVo> userVoPage = dao.getPageList("com.travel.mapper.user.UserDoMapper.getAllUser", null, pageParams);
        return new PageResultEntity<>(userVoPage);
    }

    @Override
    public String register(UserRegisterDto userRegisterDto, HttpSession session) throws Exception {
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userRegisterDto, userDo);
        String uuid = StringUtils.getUUid();
        userDo.setUserId(uuid);
        int count = dao.insert("com.travel.mapper.user.UserDoMapper.insertUser", userDo);
        if (count == 0) {
            throw new BusinessException("用户名已存在");
        }
        SessionEntity sessionEntity = new SessionEntity();
        BeanUtils.copyProperties(userDo, sessionEntity);
        return CacheUtils.setCache(sessionEntity);
    }
}
