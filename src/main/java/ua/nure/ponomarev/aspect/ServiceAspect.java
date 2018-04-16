package ua.nure.ponomarev.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.annotation.RoleAccess;
import ua.nure.ponomarev.enity.Role;
import ua.nure.ponomarev.exception.AccessPrivilegeException;
import ua.nure.ponomarev.holder.RoleHolder;

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

    @Autowired
    private RoleHolder roleHolder;

    private Map<Method, Role[]> methodRoleAccess;

    public ServiceAspect() {
        methodRoleAccess = new HashMap<>();
        initialization();
    }

    private void initialization() {
        Reflections reflections = new Reflections("ua.nure.ponomarev.service",new MethodAnnotationsScanner());
        Set<Method> methods= reflections.getMethodsAnnotatedWith(RoleAccess.class);
        for(Method m:methods){
            methodRoleAccess.put(m,getAnnotationRolesFromMethod(m));
        }
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

    @Before(value = "execution(public * *(..))&&within(ua.nure.ponomarev.service.impl.*)")
    public void checkRoleAdvice(JoinPoint joinPoint) throws AccessPrivilegeException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
       if(!isValidRole(methodRoleAccess.get(signature.getMethod()))){
           throw new AccessPrivilegeException("Access is denied");
       }
    }

    private boolean isValidRole(Role[] roles){
        Role currentUserRole = roleHolder.getRole();
        for (int i = 0; i < roles.length; i++) {
            if (currentUserRole.equals(roles[i])) {
             return true;
            }
        }
        return false;
    }
}
