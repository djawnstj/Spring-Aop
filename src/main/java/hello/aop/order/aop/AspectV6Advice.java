package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {

    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
//    @Around("hello.aop.order.aop.Pointcuts.allService()")
    public Object toTransaction(final ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            final Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

            return result;
        } catch (final Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());

            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.Pointcuts.allService()")
    public void doBefore(final JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allService()", returning = "result")
    public void toReturn(final JoinPoint joinPoint, final Object result) {
        log.info("[return] {} return ={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.allService()", throwing = "ex")
    public void doThrowing(final JoinPoint joinPoint, final Exception ex) {
        log.info("[ex] {} message ={}", joinPoint.getSignature(), ex);
    }

    @After("hello.aop.order.aop.Pointcuts.allService()")
    public void doAfter(final JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
