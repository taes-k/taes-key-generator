package com.taes.key.generator.api.aop;

import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyGeneratorType;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.util.JoinpointUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
@Log4j2
public class ProfileAdvice
{
    @Around("execution(* com.taes.key.generator.api.component.key.generator.KeyGeneratorFactory.generateNewKey(..)))")
    public Object profileKeyStringGenerator(ProceedingJoinPoint jp) throws Throwable
    {
        KeySet keySet = JoinpointUtil.getParamByClass(jp, KeySet.class);
        KeyType KeyType = keySet.getKeyType();
        KeyGeneratorType keyGeneratorType = keySet.getKeyGenerator();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //----------------------------------------------------------------------------------------------------------
        Object returnObj = jp.proceed();
        //----------------------------------------------------------------------------------------------------------
        stopWatch.stop();

        log.info("key_generator_profile:type[{}]/generator[{}]/processTime[{}ms]"
            , KeyType, keyGeneratorType, stopWatch.getTime());

        return returnObj;
    }
}
