package com.pkast.utils;

import com.pkast.modules.Resp;
import com.pkast.modules.RespRetCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@Aspect
/**
 * 提供访问鉴权以及异常捕获的切面。
 * 如果异常直接抛到界面，会造成前台打印敏感信息。
 */
public class ServiceAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAop.class);

    @Around("execution (public * com.pkast..*Service.*(..)) && !@annotation(com.pkast.utils.CheckUser)")
    public Object catchException(ProceedingJoinPoint joinPoint){
        try{
            return joinPoint.proceed();
        }catch (Throwable e){
            LOGGER.error("process error.", e);
        }
        return Resp.makeResp(RespRetCode.RET_FAIL);
    }

    @Around("execution (public * com.pkast..*Service.*(..)) && args(userWxNo, ..) && @annotation(com.pkast.utils.CheckUser)")
    public Object doCheck(ProceedingJoinPoint joinPoint, String userWxNo){
        if(!CheckValidUtil.isWxValid(userWxNo) ){
            return Resp.makeResp(RespRetCode.RET_NOTREG);
        }

        return catchException(joinPoint);
    }
}
