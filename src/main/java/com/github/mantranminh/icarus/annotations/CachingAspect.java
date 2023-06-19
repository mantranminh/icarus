package com.github.mantranminh.icarus.annotations;

import com.github.mantranminh.icarus.utils.CacheUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CachingAspect {
    private final RedissonClient redissonClient;

    @Around("@annotation(Caching)")
    public Object caching(ProceedingJoinPoint pjp, Caching Caching) throws Throwable {
        String cacheKey = CacheUtils.getParticularCacheKey(Caching.prefix(), pjp.getArgs(), Caching.argLimit());
        Object rs;
        RBucket rBucket = redissonClient.getBucket(cacheKey);
        if (rBucket.isExists()) {
            log.debug("caching#before : cache found {}", cacheKey);
            return rBucket.get();
        }
        try {
            rs = pjp.proceed();
            if (rs != null) {
                rBucket.set(rs, Caching.ttl(), TimeUnit.MILLISECONDS);
                log.debug("caching#after : got from API, then update cache {}", cacheKey);
            }
            return rs;
        } catch (Exception e) {
            log.error("caching()#catch : general exception {}", cacheKey, e);
            throw e;
        }
    }
}
