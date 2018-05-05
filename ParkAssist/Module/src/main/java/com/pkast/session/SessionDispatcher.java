package com.pkast.session;

import com.pkast.db.DBNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.stream.Stream;

public class SessionDispatcher extends DispatcherServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDispatcher.class);

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DBNameUtil.setDbName(null);
        try {
            super.doService(request, response);
        }catch (Throwable e){
            LOGGER.error("do service error.", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
