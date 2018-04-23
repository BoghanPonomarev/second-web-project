package ua.nure.ponomarev.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.nure.ponomarev.model.enity.Role;
import ua.nure.ponomarev.model.holder.RoleHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 *
 * Security filter is responsible for checking and adding role to user.
 * Role stors in session
 */
public class SecurityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private static final String SESSION_ROLE_ATTRIBUTE_NAME = "role";

    private static final Role DEFAULT_ROLE = Role.ANONYMOUS;

    @Lazy
    @Autowired
    private RoleHolder roleHolder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Security filter (" + SecurityFilter.class + ") was initiated");
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        Role role = (Role) session.getAttribute(SESSION_ROLE_ATTRIBUTE_NAME);
        if (role == null) {
            session.setAttribute(SESSION_ROLE_ATTRIBUTE_NAME, DEFAULT_ROLE);
            role = Role.ANONYMOUS;
        }
        roleHolder.setRole(role);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        logger.info("Security filter (" + SecurityFilter.class + ") was destroyed");
    }
}
