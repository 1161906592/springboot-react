package com.medicinalManage.utils;

import com.medicinalManage.common.SessionEntity;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static String setSession(HttpSession session, SessionEntity sessionEntity) {
        String md5Str = DigestUtils.md5DigestAsHex((sessionEntity.getUserId() + "").getBytes());
        session.setAttribute(md5Str, sessionEntity);
        return md5Str;
    }
    public static SessionEntity getSessionEntity(HttpServletRequest request) {
        String md5Str = request.getHeader("token");
        return (SessionEntity) request.getSession().getAttribute(md5Str);
    }
    public static void removeSession(HttpServletRequest request) {
        request.getSession().removeAttribute(request.getHeader("token"));
    }
}
