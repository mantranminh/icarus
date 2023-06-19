package com.github.mantranminh.icarus.annotations;

import com.github.mantranminh.icarus.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Aspect
@Component
public class RaceConditionAspect {

    public static final String LOCK_PROCESSING_PREFIX = "lock:processing";
    private final RedissonClient redissonClient;

    public RaceConditionAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(RaceCondition)")
    public Object raceCondition(ProceedingJoinPoint pjp, RaceCondition RaceCondition) {
        String cacheKey =
                CacheUtils.getParticularCacheKey(LOCK_PROCESSING_PREFIX, pjp.getArgs(), RaceCondition.argLimit());
        RLock lock = redissonClient.getLock(cacheKey);
        boolean isLockSuccessful = false;
        try {
            isLockSuccessful = lock.tryLock(
                    RaceCondition.waitTime(), RaceCondition.leaseTime(), TimeUnit.MILLISECONDS);
            if (!isLockSuccessful) {
                log.debug("RaceCondition#fail cannot acquire lock : {}", cacheKey);
                throw new CannotAcquireLockException("Cannot acquire lock");
            }
            return pjp.proceed();
        } catch (Throwable e) {
            log.error("RaceCondition#catch exception : {}", cacheKey, e);
            throw new RuntimeException();
        } finally {
            if (isLockSuccessful) {
                lock.unlock();
            }
            log.debug("RaceCondition#finally : {}", cacheKey);
        }
    }
}
