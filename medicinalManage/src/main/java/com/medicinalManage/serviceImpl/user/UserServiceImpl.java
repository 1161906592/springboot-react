package com.medicinalManage.serviceImpl.user;

import com.github.pagehelper.Page;
import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.common.SessionEntity;
import com.medicinalManage.model.user.UserVo;
import com.medicinalManage.utils.SessionUtils;
import com.medicinalManage.dao.Dao;
import com.medicinalManage.exception.BusinessException;
import com.medicinalManage.model.user.UserDo;
import com.medicinalManage.model.user.UserRegisterDto;
import com.medicinalManage.service.user.UserService;
import com.medicinalManage.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
        UserDo user = dao.getOne("com.medicinalManage.mapper.user.UserDoMapper.getUserByUserDo", userDo);
        if (user == null) {
            throw new BusinessException("用户名不存在!");
        }
        if (!user.getPassword().equals(userRegisterDto.getPassword())) {
            throw new BusinessException("密码错误!");
        }
        SessionEntity sessionEntity = new SessionEntity();
        BeanUtils.copyProperties(user, sessionEntity);
        return SessionUtils.setSession(session, sessionEntity);
    }

    @Override
    public void logout(HttpServletRequest request) {
        SessionUtils.removeSession(request);
    }

    @Override
    public PageResultEntity<UserVo> getAllUser(PageParams pageParams) throws Exception {
        Page<UserVo> userVoPage = dao.getPageList("com.medicinalManage.mapper.user.UserDoMapper.getAllUser", null, pageParams);
        return new PageResultEntity<>(userVoPage);
    }

    @Override
    public String register(UserRegisterDto userRegisterDto, HttpSession session) throws Exception {
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userRegisterDto, userDo);
        String uuid = StringUtils.getUUid();
        userDo.setUserId(uuid);
        int count = dao.insert("com.medicinalManage.mapper.user.UserDoMapper.insertUser", userDo);
        if (count == 0) {
            throw new BusinessException("用户名已存在");
        }
        SessionEntity sessionEntity = new SessionEntity();
        BeanUtils.copyProperties(userDo, sessionEntity);
        return SessionUtils.setSession(session, sessionEntity);
    }
}
