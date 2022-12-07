package com.bg.aspect;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.UserLog;
import com.bg.service.UserLogService;
import com.bg.util.DateTimeUtil;
import com.bg.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 用户操作通知，作记录
 *
 * @author ctp
 * @date 2022/11/29 22:49
 */
@Component
@Aspect
@Slf4j
public class UserAspect {
    @Resource
    private UserLogService userLogService;

    /**
     * 切入非查询动作
     */
    @Pointcut("execution(* com.bg.controller.*.*(..)) && !execution(* com.bg.controller.*.get*(..))")
    public void afterAllController() {
    }

    /**
     * 记录用户操作
     * 更新，登录，退出登录
     */
    @Around("afterAllController()")
    public Object aroundUserOperation(ProceedingJoinPoint pjp) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        // 计算请求所消耗时间
        long consumeTime = System.currentTimeMillis() - beginTime;
        // 日志保存
        saveUserLog(pjp,consumeTime);
        return result;
    }



    /**
     * 保存用户日志
     * @param pjp 连接点
     * @param consumeTime 消耗时间
     */
    private void saveUserLog(ProceedingJoinPoint pjp, long consumeTime) {
        // 1,获取用户ID
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();// 上下文获取容器
        HttpServletRequest request;
        if (attributes != null){
            request = attributes.getRequest();
        }else{
            return;
        }
        String token = request.getHeader(KeysProperties.TOKEN_KEY);
        if (StringUtils.isEmpty(token)){
            return;
        }
        long userId = Long.parseLong(JwtUtil.parse(token).get(KeysProperties.TOKEN_USER_ID_KEY).toString());

        UserLog userLog = new UserLog();
        userLog.setUserId(userId);

        // 2,获取执行的方法
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();

        // 3,获取Log注解描述
        Log logAnno = method.getAnnotation(Log.class);
        if (logAnno != null){
            userLog.setOperation(logAnno.value());
        }
        // 4,获取类名与方法名
        String className = pjp.getTarget().getClass().getName();
        String methodName = methodSignature.getMethod().getName();
        userLog.setMethod(className + "." + methodName);

        // 5,获取方法参数名与参数值
        Object[] args = pjp.getArgs();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);// 根据方法获取对应参数名

        if (args != null && parameterNames != null){
            StringBuilder params = new StringBuilder();  // 选择效率高的 StringBuilder
            for (int i = 0; i < args.length; i++) {
                params.append(parameterNames[i]).append(":").append(args[i]).append(" ");
            }
            userLog.setParams(params.toString());
        }

        // 6,获取IP地址
        String remoteAddr = request.getRemoteAddr();
        userLog.setIp(remoteAddr);

        // 7设置消耗时间和操作时间
        userLog.setConsumeTime(consumeTime);
        userLog.setCreateTime(DateTimeUtil.getDataTime());

        // 8,存储记录
        if(!userLogService.insertUserLog(userLog)){
            log.error("插入用户日志错误 : " + DateTimeUtil.getDataTime() + "," + System.currentTimeMillis());
        }
    }
}
