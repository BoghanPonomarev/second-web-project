package ua.nure.ponomarev.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;

/**
 * @author Bogdan_Ponamarev.
 */
@Aspect
@Component
public class DaoAspect {
    private static final Logger logger = LoggerFactory.getLogger(DaoAspect.class);

    private EntityManagerHolder entityManagerHolder;

    @Autowired
    public DaoAspect(EntityManagerHolder entityManagerHolder) {
        this.entityManagerHolder = entityManagerHolder;
    }

    @Before("execution(public * *(..))&&within(ua.nure.ponomarev.dao.impl.*))")
    public void checkIsOpenConnectionAdvice() {
        EntityManager entityManager = entityManagerHolder.getEntityManager();
        logger.debug("Connection to data base is " + ((entityManager != null && entityManager.isOpen()) ? "open" : "close")
                + System.getProperty("line.separator") + " for thread " + Thread.currentThread().getName());
    }

    @AfterThrowing(value = "execution(public * *(..))&&within(ua.nure.ponomarev.dao.impl.*))", throwing = "ex")
    public void errorLogAdvice(JoinPoint joinPoint,Exception ex) throws Throwable{
        logger.error(ex + " was thrown in " + joinPoint.getSignature().getName() + " method");
    }
}

