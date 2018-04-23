package ua.nure.ponomarev.model.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bogdan_Ponamarev.
 */
@Aspect
@Component
public class DaoAspect {
    private static final Logger logger = LoggerFactory.getLogger(DaoAspect.class);
    private SessionFactory sessionFactory;


    @Autowired
    public DaoAspect(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Logging advice before every dao method
     */
    @Before("execution(public * *(..))&&within(ua.nure.ponomarev.model.dao.impl.*))")
    public void checkIsOpenConnectionAdvice() {
        Session session= sessionFactory.getCurrentSession();
        logger.debug("Connection to data base is " + ((session != null && session.isOpen()) ? "open " : "close ")
               + ((session != null &&session.getTransaction()!=null
                && session.getTransaction().isActive())
                ?"with transaction":"without transaction"));
    }

    /**
     * Logging method that is occur when throws exception in dao layer
     * @param joinPoint - standard aspect parameter for obtaining detail information
     * @param ex - exception that was thrown
     * @throws Throwable - exception that will wrap the original one
     */
    @AfterThrowing(value = "execution(public * *(..))&&within(ua.nure.ponomarev.model.dao.impl.*))", throwing = "ex")
    public void errorLogAdvice(JoinPoint joinPoint, Exception ex) throws Throwable {
        logger.error(ex + " was thrown in " + joinPoint.getSignature().getName() + " method");
    }
}

