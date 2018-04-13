package com.pkast.session;

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
    public static final String SESS_ID_NAME = "JSESSIONID";
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDispatcher.class);

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<Cookie> session = request.getCookies() == null ? null:
                Stream.of(request.getCookies()).filter(cookie ->SESS_ID_NAME.equals(cookie.getName())).findAny();
        String sessionId = null;
        if(session != null && session.isPresent()){
            sessionId = session.get().getValue();
            LOGGER.debug("{} session exists." , sessionId);
        }
        else{
            HttpSession sess = request.getSession();
            sessionId = sess.getId();
            LOGGER.debug("{} created." ,sessionId);
        }
        SessionThreadLocal.setSessionId(sessionId);
        try {
            super.doService(request, response);
        }catch (Throwable e){
            LOGGER.error("do service error.", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        finally {
            SessionThreadLocal.clearSessionId();
        }
    }
}
