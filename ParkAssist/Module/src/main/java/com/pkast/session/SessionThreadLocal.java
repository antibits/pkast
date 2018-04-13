package com.pkast.session;

public class SessionThreadLocal {
    private static final ThreadLocal<String> sessionId = new ThreadLocal<>();

    public static void setSessionId(String sessionId){
        SessionThreadLocal.sessionId.set(sessionId);
    }

    public static String getSessionId(){
        return SessionThreadLocal.sessionId.get();
    }

    public static void clearSessionId(){
        SessionThreadLocal.sessionId.remove();
    }
}
