package ua.nure.ponomarev.model.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.model.annotation.RoleAccess;
import ua.nure.ponomarev.model.enity.Role;
import ua.nure.ponomarev.model.exception.AccessPrivilegeException;
import ua.nure.ponomarev.model.holder.RoleHolder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Bogdan_Ponamarev.
 */
@Aspect
@Component
public class ServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);
    /**
     * Field that in runtime holds current user role
     */
    private RoleHolder roleHolder;
    /**
     * Map that holds methods marked by {@link RoleAccess} annotation and appropriate roles
     */
    private Map<Method, Role[]> methodRoleAccess;

    @Autowired
    public ServiceAspect(RoleHolder roleHolder) {
        methodRoleAccess = new HashMap<>();
        this.roleHolder = roleHolder;
        init();
    }

    /**
     * Init method that search all methods with {@link RoleAccess} annotation and
     * puts their all into map
     */
    private void init() {
        Reflections reflections = new Reflections("ua.nure.ponomarev.model.service", new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(RoleAccess.class);
        for (Method m : methods) {
            methodRoleAccess.put(m, getAnnotationRolesFromMethod(m));
        }
        logger.info("Service aspect was initiated");
    }

    private Role[] getAnnotationRolesFromMethod(Method method) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(RoleAccess.class)) {
                return ((RoleAccess) annotation).role();
            }
        }
        return null;
    }

    @Before(value = "execution(public * *(..))&&within(ua.nure.ponomarev.model.service.impl.*)")
    public void checkRoleAdvice(JoinPoint joinPoint) throws AccessPrivilegeException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        if (!isValidRole(methodRoleAccess.get(signature.getMethod()))) {
            logger.info("Access to " + signature.getMethod());
            throw new AccessPrivilegeException("Access is denied to " + roleHolder.getRole());
        }
    }

    private boolean isValidRole(Role[] roles) {
        if (roles == null) {
            return true;
        }
        Role currentUserRole = roleHolder.getRole();
        for (Role role : roles) {
            if (currentUserRole.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
