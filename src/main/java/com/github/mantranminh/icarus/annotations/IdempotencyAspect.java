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

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class IdempotencyAspect {
    public static final String IDEMPOTENCY_PREFIX = "idempotency";
    private final RedissonClient redissonClient;

    @Around("@annotation(Idempotency)")
    public Object idempotency(ProceedingJoinPoint pjp, Idempotency Idempotency) {
        int idx = Idempotency.kIndex();
        String cacheKey = CacheUtils.getParticularCacheKey(IDEMPOTENCY_PREFIX, String.valueOf(pjp.getArgs()[idx]));
        RBucket rBucket = redissonClient.getBucket(cacheKey);
        if (rBucket.isExists()) {
            log.debug("idempotency()#before : cache found {}", cacheKey);
            return rBucket.get();
        }

        try {
            Object rs = pjp.proceed();
            if (rs != null) {
                rBucket.set(rs, Idempotency.ttl(), TimeUnit.MILLISECONDS);
                log.debug("idempotency()#after : processed, update cache {}", cacheKey);
            }
            return rs;
        } catch (Throwable e) {
            log.debug("idempotency()#catch : general error {}", cacheKey, e);
            throw new RuntimeException(e);
        }
    }
}
